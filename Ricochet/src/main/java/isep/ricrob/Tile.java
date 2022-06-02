package isep.ricrob;

import javafx.scene.image.ImageView;

public class Tile {

    int left;
    int right;
    int up;
    int down;
    int mid;

    public Tile(int left, int right, int up, int down, int mid) {
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
        this.mid = mid;
    }

    public Tile(Tile t){
        this.left = t.getLeft();
        this.right = t.getRight();
        this.up = t.getUp();
        this.down = t.getDown();
        this.mid = t.getMid();
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getUp() {
        return up;
    }

    public int getDown() {
        return down;
    }

    public int getMid() {
        return mid;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    @Override
    public String toString() {
        return "{" + left + " "+ right + " " + up + " " + down + " " + mid + '}';
    }
}
