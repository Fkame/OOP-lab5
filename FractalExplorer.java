import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {
	
	private int width;
	private int height;
	
	private JImageDisplay display;
	private Rectangle2D.Double range;
	private JFrame frame;
	private JButton button;
	private Mandelbrot mandelbrot;
	
	
	/*
	* Конструкторы
	*/
	public FractalExplorer() {
		this(600);
	}
	
	public FractalExplorer(int size) {
		this(size, size);
	}
	
	public FractalExplorer(int width, int height) {
		this.width = width;
		this.height = height;
		
		// Создание объекта, содержащего диапазон
		this.range = new Rectangle2D.Double();
		
		// Создание класса Mandelbrot
		this.mandelbrot = new Mandelbrot();
	}
	
	/*
	* Создание формы с компонентами
	*/
	public void createAndShowGUI() {
		// Создание формы
		this.frame = new JFrame("Fraktalz");
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.frame.setSize(this.width, this.height);
		this.frame.setResizable(false); 
		
		// Добавление кнопки
		this.button = new JButton("Reset display");
		frame.getContentPane().add(BorderLayout.SOUTH, this.button);
		
		// Добавить подгон квадратной области после добавления кнопки
		// ...
		
		// Создание панели рисования
		this.display = new JImageDisplay(this.frame.getWidth(), this.frame.getHeight());
		frame.getContentPane().add(BorderLayout.CENTER, this.display);
		
		frame.setVisible(true);
	}
	
	/*
	* Отрисовка фрактала.
	* В цикле идёт проход по всем пикселям и определяется, входит ли он в площадь фрактала
	* Степень входа определяется цветом пикселя.
	*/
	public void drawFractal() {
		
		// Задание пределов фрактала
		mandelbrot.getInitialRange(range);
		System.out.println("Range = " + range.x + ", " + range.y + ", " + range.width + ", " + range.height + "\n");
		
		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				
				// Преобразование координат плоскости пикселей в координаты мнимой плоскости
				double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, display.getWidth(), x);
				double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, display.getHeight(), y);
				
				// Определение входа точки в множество Мандельброта
				int numOfIter = mandelbrot.numIterations(xCoord, yCoord);
				
				// Логирование количества итераций каждой точки
				//if (numOfIter > 50)
					//System.out.println("x = " + x + ", y = " + y + ", xCoord = " + xCoord + ", yCoord = " + yCoord + ", iteration = " + numOfIter);
				
				int rgbColor;
				if (numOfIter != -1) {
					float hue = 0.7f + (float) numOfIter / 200f; 
					rgbColor = Color.HSBtoRGB(hue, 1f, 1f); 
					//display.drawPixel(x, y, Color.pink);
				} 
				else {
					rgbColor = Color.HSBtoRGB(0, 0, 0); 
					//display.drawPixel(x, y, Color.black);
				}
				
				
				display.drawPixel(x, y, new Color(rgbColor));
				
			}
		}
	}
	
	/*
	* Точка входа
	*/
	public static void main(String[] args) {
		FractalExplorer explorer = new FractalExplorer(700);
		explorer.createAndShowGUI();
		explorer.drawFractal();
	}
}