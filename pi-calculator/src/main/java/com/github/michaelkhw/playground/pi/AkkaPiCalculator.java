package com.github.michaelkhw.playground.pi;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;
import com.github.michaelkhw.playground.pi.msg.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by michael on 22/3/15.
 */
public class AkkaPiCalculator implements PiCalculator, AutoCloseable {
    private final ActorSystem actorSystem;
    private ArrayBlockingQueue<Double> resultQueue;
    private final int iterationSize = 1000000;
    private final int noOfWorker = 4;
    private ActorRef resultPublisher;
    private ActorRef master;

    public AkkaPiCalculator() {
        actorSystem = ActorSystem.create("PiSystem");
        resultQueue = new ArrayBlockingQueue<>(10);

        init();
    }

    private void init() {
        resultPublisher = actorSystem.actorOf(Props.create(ResultPublisher.class, resultQueue), "resultPublisher");
        master = actorSystem.actorOf(Props.create(Master.class, noOfWorker, iterationSize, resultPublisher), "master");
    }

    @Override
    public double calculate(long iterations) {
        resultQueue.clear();
        master.tell(new CalcResetMsg(), null);
        master.tell(new CalcRequestMsg(iterations), null);

        try {
            Double d = resultQueue.take();
            return d;
        } catch (InterruptedException e) {
            throw new RuntimeException("Cannot complete calculation, calculation interrupted", e);
        }
    }

    public void close() {
        actorSystem.shutdown();
    }

    public static class Master extends UntypedActor {
        private final int iterationSize;
        private final ActorRef listener;
        private final ActorRef workerRouter;
        private double pi;
        private long iterationCalcResultReceived;
        private long iterationCalcResultExpected;

        public Master(final int nrOfWorkers, int iterationSize, ActorRef listener) {
            this.iterationSize = iterationSize;
            this.listener = listener;

            workerRouter = this.getContext().actorOf(Props.create(Worker.class).withRouter(new RoundRobinPool(nrOfWorkers)),
                    "workerRouter");
        }

        @Override
        public void onReceive(Object message) throws Exception {
            if (message instanceof CalcResetMsg) {
                pi = 0;
                iterationCalcResultReceived = 0;
                iterationCalcResultExpected = 0;
            }

            if (message instanceof CalcRequestMsg) {
                long iterations = ((CalcRequestMsg) message).getIterations();
                for (long i = 0; i < iterations; i += iterationSize) {
                    iterationCalcResultExpected++;
                    workerRouter.tell(new IterationCalcRequestMsg(i, Math.min(i + iterationSize, iterations)), getSelf());
                }

                return;
            }

            if (message instanceof IterationCalcResultMsg) {
                IterationCalcResultMsg result = (IterationCalcResultMsg) message;
                pi += result.getValue();
                iterationCalcResultReceived += 1;
                if (iterationCalcResultReceived == iterationCalcResultExpected) {
                    listener.tell(new CalcResultMsg(pi), getSelf());
                }

                return;
            }

            unhandled(message);
        }
    }

    public static class Worker extends UntypedActor {
        @Override
        public void onReceive(Object o) throws Exception {
            if (o instanceof IterationCalcRequestMsg) {
                IterationCalcRequestMsg msg = (IterationCalcRequestMsg) o;
                double d = SimplePiCalculator.calculateCore(msg.getStart(), msg.getEnd());

                getSender().tell(new IterationCalcResultMsg(d), getSelf());

                return;
            }

            unhandled(o);
        }
    }

    public static class ResultPublisher extends UntypedActor {
        private BlockingQueue<Double> calcResultSink;

        public ResultPublisher(BlockingQueue<Double> calcResultSink) {
            this.calcResultSink = calcResultSink;
        }

        @Override
        public void onReceive(Object o) throws Exception {
            if (o instanceof CalcResultMsg) {
                CalcResultMsg msg = (CalcResultMsg) o;
                calcResultSink.put(msg.getPi());

                return;
            }

            unhandled(o);
        }
    }
}
