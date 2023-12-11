import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class singletonPattern {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PagingSystem pagingSystem = PagingSystem.getInstance();

        HelpDesk deskStation1 = new HelpDesk("Help Desk 1");
        HelpDesk deskStation2 = new HelpDesk("Help Desk 2");
        HelpDesk deskStation3 = new HelpDesk("Help Desk 3");

        System.out.print("Enter number of tickets needed: ");
        int ticket = sc.nextInt();
        for(int i=0; i<ticket; i++){
            pagingSystem.issueQueueNumber();
        }

        System.out.print("\n---Now Serving--- \n");

        for (int i=0; i<ticket;) {
            deskStation1.pagingVisitor();
            i++;
            deskStation2.pagingVisitor();
            i++;
            deskStation3.pagingVisitor();
            i++;
        }

        System.out.print("\nEnter number to reset to: ");
        int reset = sc.nextInt();

        deskStation1.resetQueueNumber(reset);

        System.out.print("\nEnter number of tickets needed: ");
        ticket = sc.nextInt();

        for(int i=0; i<ticket; i++){
            pagingSystem.issueQueueNumber();
        }

        System.out.print("\n---Now Serving--- \n");

        for (int i=0; i<ticket;) {
            deskStation1.pagingVisitor();
            i++;
            deskStation2.pagingVisitor();
            i++;
            deskStation3.pagingVisitor();
            i++;
        }
    }
}

class PagingSystem {
    private static PagingSystem instance;
    private int latestQueueNumber;
    private Queue<Integer> visitorQueue = new LinkedList<>();

    private PagingSystem() {
        this.latestQueueNumber = 1;
    }

    public static synchronized PagingSystem getInstance() {
        if (instance == null)
            instance = new PagingSystem();
        return instance;
    }

    public synchronized void issueQueueNumber() {
        int queueNumber = latestQueueNumber;
        visitorQueue.add(queueNumber);
        System.out.println("Issuing queue number: " + queueNumber);
        latestQueueNumber++;
    }

    public synchronized int pageNextInQueue() {
        if (visitorQueue.isEmpty())
            return 0;
        return visitorQueue.remove();
    }

    public synchronized void resetQueueNumber(int newQueueNumber) {
        latestQueueNumber = newQueueNumber;
        System.out.println("Queue number reset to: " + latestQueueNumber);
    }
}

class HelpDesk {
    private String deskNumber;

    public HelpDesk(String deskNumber) {
        this.deskNumber = deskNumber;
    }

    public void pagingVisitor() {
        PagingSystem pagingSystem = PagingSystem.getInstance();
        int nextInQueue = pagingSystem.pageNextInQueue();
        if (nextInQueue == 0) {
            System.out.println("All tickets given are paged");
        } else {
            System.out.println(deskNumber + " is calling for visitor: " + nextInQueue);
        }
    }

    public void resetQueueNumber(int newQueueNumber) {
        PagingSystem pagingSystem = PagingSystem.getInstance();
        pagingSystem.resetQueueNumber(newQueueNumber);
    }
}