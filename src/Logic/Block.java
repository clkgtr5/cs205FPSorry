package Logic;

import java.util.ArrayList;

public class Block {
    int id;
    Color color;
    Block previousBlock, nextBlock;
    Block nextSafetyBlock;

    public Slidiness slideStatus;

    ArrayList<Pawn> pawnsHere;

    public Block(Color inColor,  int id) {
        pawnsHere = new ArrayList<>();
        this.color = inColor;
        this.id = id;
        this.slideStatus = Slidiness.NOT;
    }

    //returns the first safety block if it's the right color and location
    public Block getNextBlock(Color pawnColor) {
        if (color == pawnColor && nextSafetyBlock != null) {
            return nextSafetyBlock;
        }
        else {
            return getNextBlock();
        }
    }

    public Pawn getPawn() {
        if (pawnsHere.size() == 0) {
            return null;
        }
        return pawnsHere.get(0);
    }

    public void setSlideStatus(Slidiness slideIn) {
        this.slideStatus = slideIn;
    }
    public Slidiness getSlideStatus() {
        return slideStatus;
    }



    private Block getNextBlock() {
        return nextBlock;
    }
    public void setNextBlock(Block nextBlock) {
        this.nextBlock = nextBlock;
    }

    public Block getNextSafetyBlock() {
        return nextSafetyBlock;
    }
    public void setNextSafetyBlock(Block nextSafetyBlock) {
        this.nextSafetyBlock = nextSafetyBlock;
    }

    public Block getPreviousBlock() {
        return previousBlock;
    }
    public void setPreviousBlock(Block previousBlock) {
        this.previousBlock = previousBlock;
    }

    public boolean place(Pawn pawn) {
        if (id != -1 && pawnsHere.size() != 0){
            //pawnsHere.get(0).getBounced();
            pawnsHere.remove(0);
            pawnsHere.add(pawn);
            return false;
        }
        else {
            pawnsHere.add(pawn);
            return true;
        }
    }
}