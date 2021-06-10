package mc322.lab07;

import mc322.lab07.blocks.Block;
import mc322.lab07.blocks.Sand;
import mc322.lab07.blocks.Rock;

import mc322.lab07.util.Position;


public class World {
    // blocks[height][width]
    private int width = 20;
    private int height = 20;
    private Block[][] blocks = new Block[width][height];

    public World() {
        for (int i = 0; i < 5; i++) {
            Position pos = new Position(10, 19 - i);
            Sand sandBlock = new Sand(pos, this);
            addBlock(sandBlock);
        }

        for (int i = 0; i < width; i++) {
            Position pos = new Position(i, 0);
            Rock rockBlock = new Rock(pos, this);
            addBlock(rockBlock);
        }
    }

    public World(int width, int height) {
        // calls the World() constructor
        // don't know if this is bad or not
        this();
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    // make sure that no blocks are overwritten?
    public void addBlock(Block block) {
        Position pos = block.getPosition();
        if (isValidPosition(pos)) {
            int x = block.getPosition().x;
            int y = block.getPosition().y;
            blocks[x][y] = block;
        } else {
            System.err.printf("Invalid block add at %s: position not empty\n", pos);
        }
    }

    private boolean isValidPosition(Position position) {
        return position.x >= 0 && position.y >= 0 &&
            position.x < width && position.y < height;
    }

    public Block getBlockAt(Position position) {
        if (!isValidPosition(position)) {
            System.err.printf("Invalid access to position %s: out of bounds\n", position);
            return null;
        } else {
            return blocks[position.x][position.y];
        }
    }

    public Block getBlockAt(int x, int y) {
        return getBlockAt(new Position(x, y));
    }

    public void removeBlock(Block block) {
        Position pos = block.getPosition();
        if (getBlockAt(pos) == null) {
            return;
        } else {
            blocks[pos.x][pos.y] = null;
        }
    }
}
