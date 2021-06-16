package com.javasegfault.shroomite;

public class StatusBarInfo {
	private String selectedTool;
	private BlockType selectedBlockType;
	private int gridWidth;
	private int gridHeight;
	private int mouseX;
	private int mouseY;
	
	public StatusBarInfo() {
		this.selectedTool = null;
		this.selectedBlockType = null;
		this.gridWidth = -1;
		this.gridHeight = -1;
		this.mouseX = -1;
		this.mouseY = -1;
	}
	
	public String getSelectedTool() {
		return selectedTool;
	}
	
	public void setSelectedTool(String selectedTool) {
		this.selectedTool = selectedTool;
	}
	
	public BlockType getSelectedBlockType() {
		return selectedBlockType;
	}
	
	public void setSelectedBlockType(BlockType selectedBlockType) {
		this.selectedBlockType = selectedBlockType;
	}
	
	public int getGridWidth() {
		return gridWidth;
	}
	
	public int getGridHeight() {
		return gridHeight;
	}
	
	public void setGridDimensions(int gridWidth, int gridHeight) {
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
	}
	
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}
	
	public void setMousePosition(int mouseX, int mouseY) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}
	
	public String getLabelText() {
		return String.format("Selected tool: %s | Selected block type: %s | Mouse: %d x %d | Grid: %d x %d", 
				selectedTool, selectedBlockType, mouseX, mouseY, gridWidth, gridHeight);
	}
}
