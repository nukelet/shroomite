package com.javasegfault.shroomite.editor;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class GridTable extends Table {
    private final WorldEditorScreen worldEditorScreen;
    private Grid grid;
    private GridTableCell cells[][];

    public GridTable(Skin skin, final WorldEditorScreen worldEditorScreen, Grid grid) {
        super(skin);
        this.worldEditorScreen = worldEditorScreen;
        setGrid(grid);
        defaults().grow();
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
        this.cells = new GridTableCell[grid.getHeight()][grid.getWidth()];
        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                this.cells[i][j] = new GridTableCell(worldEditorScreen, grid.getCellAt(j, i));
            }
        }
        update();
    }

    /**
     * Atualiza os drawables de todas as células para ficarem de acordo com
     * o tipo de bloco que armazenam.
     *
     * Deve ser chamado sempre que a grid associada sofrer alterações
     * (alguma de suas células mudar o tipo de bloco armazenado, por exemplo).
     */
    public void updateCellsDrawables() {
        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                cells[i][j].updateDrawable();
            }
        }
    }

    private void update() {
        clear();
        for (int i = grid.getHeight() - 1; i >= 0; i--) {
            for (int j = 0; j < grid.getWidth(); j++) {
                add(cells[i][j]);
            }
            row();
        }
    }
}
