import java.util.*;

class Team {

    final String name;
    final int points;
    final int goalDiff;

    Team(String name, int points, int goalDiff) {
        this.name = name;
        this.points = points;
        this.goalDiff = goalDiff;
    }

    @Override
    public String toString() {
        return name + "(" + points + "," + goalDiff + ")";
    }
}

class Leaderboard<T> {

    private final List<T> items = new ArrayList<>();

    private final Comparator<? super T> order;

    Leaderboard(Comparator<? super T> order) {
        this.order = order;
    }

    void add(T item) {
        items.add(item);
    }

    List<T> ranking() {

        List<T> result = new ArrayList<>(items);

        result.sort(order);

        return result;
    }
}

public class Q5 {

    public static void main(String[] args) {

        Comparator<Team> byRank =
            Comparator.comparingInt((Team t) -> t.points)
                .reversed()
                .thenComparing(
                    Comparator.comparingInt(
                        (Team t) -> t.goalDiff
                    ).reversed()
                )
                .thenComparing(t -> t.name);

        Leaderboard<Team> board =
                new Leaderboard<>(byRank);

        board.add(new Team("Panthers", 9, 4));
        board.add(new Team("Eagles", 9, 6));
        board.add(new Team("Cobras", 6, 1));
        board.add(new Team("Bisons", 9, 4));

        System.out.println(board.ranking());

        System.out.println("\nSecond Test:");

        Leaderboard<Team> board2 =
                new Leaderboard<>(byRank);

        board2.add(new Team("Tigers", 12, 5));
        board2.add(new Team("Lions", 12, 8));
        board2.add(new Team("Bears", 10, 3));
        board2.add(new Team("Wolves", 12, 5));

        System.out.println(board2.ranking());
    }
}