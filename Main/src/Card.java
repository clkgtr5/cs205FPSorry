package Main.src;

//enum cardValue{0,1,2,3,4,5,7,8,10,11,12}

public enum Card {
    SORRY(0,"Take any one pawn from Start and move it directly to a square occupied by any opponent's pawn, sending that pawn back to its own Start. A Sorry! card cannot be used on an opponent's pawn in a Safety Zone. If there are no pawns on the player's Start, or no opponent's pawns on any squares outside Safety Zones, the turn is lost."),
    ONE(1,"Move a pawn from Start or move a pawn one space forward."),
    TWO(2,"Move a pawn from Start or move a pawn two spaces forward. Drawing a two entitles the player to draw again at the end of his or her turn. If the player cannot use a two to move, he or she can still draw again."),
    THREE(3,"Move a pawn three spaces forward."),
    FOUR(4,"Move a pawn four spaces backward."),
    FIVE(5,"Move a pawn five spaces forward."),
    SEVEN(7,"Move one pawn seven spaces forward, or split the seven spaces between two pawns (such as four spaces for one pawn and three for another). This makes it possible for two pawns to enter Home on the same turn, for example. The seven cannot be used to move a pawn out of Start, even if the player splits it into a six and one or a five and two. The entire seven spaces must be used or the turn is lost. You may not move backwards with a split."),
    EIGHT(8,"Move a pawn eight spaces forward."),
    TEN(10,"Move a pawn 10 spaces forward or one space backward. If none of a player's pawns can move forward 10 spaces, then one pawn must move back one space."),
    ELEVEN(11,"Move 11 spaces forward, or switch the places of one of the player's own pawns and an opponent's pawn. A player that cannot move 11 spaces is not forced to switch and instead can forfeit the turn. An 11 cannot be used to switch a pawn that is in a Safety Zone."),
    TWELVE(12,"Move a pawn 12 spaces forward.");

    private final int numb;
    private final String reminderText;
    Card(int numb,String reminderText){
        this.numb=numb;
        this.reminderText=reminderText;
    }

    /*
    cardValue val;
    String reminderText;

    Card(cardValue inputCV, String inputRT){
        val=inputCV;
        reminderText=inputRT;
    }

    public cardValue getVal() {
        return val;
    }

    public String getReminderText() {
        return reminderText;
    }
    */
}