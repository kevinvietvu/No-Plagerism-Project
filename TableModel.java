import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel implements ModelListener
{
	private Canvas canvas;
	private static final int TOTAL_COLUMN = 4;
	private static final int X = 0;
	private static final int Y = 1;
	private static final int WIDTH = 2;
	
	public TableModel() 
	{
		canvas = null;
	}
	
	/**
	 * Returns the columns name
	 * @param column
	 */
	@Override
	public String getColumnName(int column) 
	{
		if (column == X)
		{
			return "X";
		}
		else if (column == Y)
		{
			return "Y";
		}
		else if (column == WIDTH)
		{
			return "WIDTH";
		}
		else
			return "HEIGHT";
	}
	
	/**
	 * Sets canvas to table model
	 * @param canvas
	 */
	public void setCanvas(Canvas canvas) 
	{
		this.canvas = canvas;
	}
	
	/**
	 * Returns the number of columns in the table
	 * @return int 
	 */
	@Override
	public int getColumnCount()
	{
		return TOTAL_COLUMN;
	}

	/**
	 * Returns the number of rows in the table
	 * @return int
	 */
	@Override
	public int getRowCount()
	{
		if (canvas != null)
			return canvas.getShapesList().size();
		else
			return 0;
	}

	/**
	 * Returns the value of a certain index in the table
	 * @param x
	 * @param y
	 * @return Object
	 */
	@Override
	public Object getValueAt(int x, int y)
	{
		DShapeModel info = canvas.getShapesList().get(x).getModel();
		if (y == X)
		{
			return info.getX();
		}
		else if (y == Y)
		{
			return info.getY();
		}
		else if (y == WIDTH)
		{
			return info.getWidth();
		}
		else
			return info.getHeight();
	}

	/**
	 * Notifies table if a model has changed and updates table
	 */
	@Override
	public void modelChanged(DShapeModel model)
	{
		ArrayList<DShape> shapes = canvas.getShapesList();
		for (int i = 0; i < shapes.size(); i++) 
		{
			if (shapes.get(i).getModel() == model) 
			{
				fireTableRowsUpdated(i, i);
				break;
			}
		}
	}
}
