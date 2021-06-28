package com.javasegfault.shroomite.editor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.javasegfault.shroomite.blocks.BlockType;
import com.javasegfault.shroomite.TextureName;
import com.javasegfault.shroomite.util.Position;

public class Grid {
    private int width;
    private int height;
    private GridCell cells[][];

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new GridCell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.cells[i][j] = new GridCell(new Position(j, i), BlockType.AIR);
            }
        }
    }

    public Grid() {
        this(16, 16);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GridCell getCellAt(int x, int y) {
        return cells[y][x];
    }

    public void setCellAt(int x, int y, GridCell cell) {
        this.cells[y][x] = cell;
    }

    public BlockType getCellBlockTypeAt(int x, int y) {
        return cells[y][x].getBlockType();
    }

    public void setCellBlockTypeAt(int x, int y, BlockType blockType) {
        this.cells[y][x].setBlockType(blockType);
    }

    public void removeCellBlockTypeAt(int x, int y) {
        this.cells[y][x].removeBlockType();
    }

    public TextureName getCellBlockTypeTextureNameAt(int x, int y) {
        return cells[y][x].getBlockTypeTextureName();
    }

    public void clear() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j].removeBlockType();
                cells[i][j].removeEntityType();
            }
        }
    }

    public void saveState(String fileName) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(fileName));
            writer.printf("%d,%d\n", width, height);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    GridCell cell = cells[i][j];
                    if (cell.hasBlockType()) {
                        writer.printf("%d:%d,%s\n", cell.getPosition().getX(),
                                cell.getPosition().getY(), cell.getBlockType().toValue());
                    } else {
                        writer.printf("%d:%d,AIR\n", cell.getPosition().getX(),
                                cell.getPosition().getY());
                    }
                }
            }

            int entityCount = getEntityCount();
            writer.printf("%d\n", entityCount);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    GridCell cell = cells[i][j];
                    if (cell.hasEntity()) {
                        writer.printf("%d:%d,%s\n", cell.getPosition().getX(),
                                cell.getPosition().getY(), cell.getEntityType());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private int getEntityCount() {
        int result = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (cells[i][j].hasEntity()) {
                    result++;
                }
            }
        }
        return result;
    }

    public void loadState(String fileName) {
        // TODO Lidar com as exceções que podem ocorrer por causa de arquivo no formato errado
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            if (line == null) {
                return;
            }

            String gridWidthHeight[] = line.split(",");
            width = Integer.parseInt(gridWidthHeight[0]);
            height = Integer.parseInt(gridWidthHeight[1]);
            cells = new GridCell[height][width];

            for (int i = 0; i < width*height; i++) {
                line = reader.readLine();
                String positionBlockType[] = line.split(",");
                String posXPosY[] = positionBlockType[0].split(":");
                int posX = Integer.parseInt(posXPosY[0]);
                int posY = Integer.parseInt(posXPosY[1]);
                Position position = new Position(posX, posY);
                BlockType blockType = BlockType.fromValue(positionBlockType[1]);
                cells[posY][posX] = new GridCell(position, blockType);
            }

            line = reader.readLine();
            if (line != null) {
                int entityCount = Integer.parseInt(line);
                for (int i = 0; i < entityCount; i++) {
                    line = reader.readLine();
                    String positionEntityType[] = line.split(",");
                    String posXPosY[] = positionEntityType[0].split(":");
                    int posX = Integer.parseInt(posXPosY[0]);
                    int posY = Integer.parseInt(posXPosY[1]);
                    String entityType = positionEntityType[1];
                    cells[posY][posX].setEntityType(entityType);
                }
            }
        } catch (IOException e) {
            // Indicar este erro para o usuário de forma visual, provavelmente através de um Dialog,
            // mostrando também o que deve ser feito para resolver este problema.
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Grid(width=%d, height=%d)", width, height);
    }
}
