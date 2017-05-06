package netpackages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

class PackTo {
    private int incomingTime, processingTime, startProcTime, endProcTime, timeToPrint;

    public PackTo(int incomingTime, int processingTime) {
        this.incomingTime = incomingTime;
        this.processingTime = processingTime;
    }

    public void setStartProcTime(int endPrevProcTime) {
        this.startProcTime = (incomingTime < endPrevProcTime) ? endPrevProcTime : incomingTime;
        setTimeToPrint(startProcTime);
    }

    public void setEndProcTime() {
        this.endProcTime = processingTime + startProcTime;
    }

    public int getEndProcTime() {
        return endProcTime;
    }

    public int getIncomingTime() {
        return incomingTime;
    }

    public int getTimeToPrint() {
        return timeToPrint;
    }

    public void setTimeToPrint(int timeToPrint) {
        this.timeToPrint = timeToPrint;
    }

    @Override
    public String toString() {
        return "PackTo{" +
                "incomingTime=" + incomingTime +
                ", processingTime=" + processingTime +
                ", startProcTime=" + startProcTime +
                ", endProcTime=" + endProcTime +
                ", timeToPrint=" + timeToPrint +
                '}';
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> tmp = Arrays.stream(reader.readLine().split("\\s"))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());

        final int SIZE = Integer.valueOf(tmp.get(0));
        final int PKG_CNT = Integer.valueOf(tmp.get(1));

        // времена начала обработки или -1
        final List<PackTo> times = new ArrayList<>();

        // буфер
        final Deque<PackTo> queue = new ArrayDeque<>();

        for (int i = 0; i < PKG_CNT; i++) {

            // peek retrieves, but does not remove, the head of this queue, or returns null if this queue is empty
            PackTo packToPrev = (queue.isEmpty()) ? new PackTo(0, 0) : queue.peekLast();

            // input next pair arrival - duration
            List<Integer> currPackage = Arrays.stream(reader.readLine().split("\\s"))
                    .mapToInt(Integer::parseInt).boxed()
                    .collect(Collectors.toList());

            PackTo packTo = new PackTo(currPackage.get(0), currPackage.get(1));
            packTo.setStartProcTime(packToPrev.getEndProcTime());
            packTo.setEndProcTime();

            if (!queue.isEmpty())
                while (queue.peekFirst() != null && packTo.getIncomingTime() >= queue.peekFirst().getEndProcTime()) queue.pollFirst();

            // и добавляем в очередь пришедший пакет
            queue.addLast(packTo);

            // если очередь превысила размер буфера, то пишем -1
            if (queue.size() > SIZE) {
                packTo.setTimeToPrint(-1);
                queue.pollLast();
            }

            times.add(packTo);
        }

        if (times.size() > 0) times.stream().forEach(p -> System.out.println(p.getTimeToPrint()));
        //if (times.size() > 0) times.stream().forEach(System.out::println);
    }
}
