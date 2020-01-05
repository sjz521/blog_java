package cn.edu.tzc.blog.domain;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import com.mysql.jdbc.Buffer;

public class VerifyCode {
	private int w = 70;
	private int h = 35;
	private Random r = new Random();
	private String[] fontNames = {"宋体","华文楷体","黑体","微软雅黑","楷体 _GB2312"};
	//可选字符
	private String codes = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
	//背景色，白色
	private Color bgColor = new Color(255,255,255);
	private String text;
	
	private Color randomColor() {
		int red = r.nextInt(150);
		int green = r.nextInt(150);
		int blue = r.nextInt(150);
		return new Color(red,green,blue);
	}
	
	private Font randomFont() {
		int index = r.nextInt(fontNames.length);
		String fontName = fontNames[index];
		int style = r.nextInt(4);//样式：0表无样式，1表粗体，2表斜体，3表粗体+斜体
		int size = r.nextInt(5)+24;
		return new Font(fontName,style,size);
	}
	
	private void drawLine(BufferedImage image) {
		int num = 3;
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		//画3条干扰线
		for(int i = 0;i<num;i++) {
			int x1 = r.nextInt(w);
			int y1 = r.nextInt(h);
			int x2 = r.nextInt(w);
			int y2 = r.nextInt(h);
			g2.setStroke(new BasicStroke(1.5F));
			g2.setColor(Color.BLUE);
			g2.drawLine(x1, y1, x2, y2);
		}
	}
	
	private char randomChar() {
		int index = r.nextInt(codes.length());
		return codes.charAt(index);
	}
	
	private BufferedImage createImage() {
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		g2.setColor(this.bgColor);
		g2.fillRect(0, 0, w, h);
		return image;
	}
	
	//得到验证码
	public BufferedImage getImage() {
		BufferedImage image = createImage();//创建图片缓冲区
		Graphics2D g2 = (Graphics2D)image.getGraphics();//得到绘制环境
		StringBuilder sb = new StringBuilder();
		
		//向图片中画4个字符
		for(int i=0;i<4;i++) {
			String s = randomChar()+"";
			sb.append(s);
			float x = i*1.0F*w/4;//设置当前字符的x轴坐标
			g2.setFont(randomFont());
			g2.setColor(randomColor());
			g2.drawString(s, x, h-5);
		}
		
		this.text = sb.toString();
		drawLine(image);//添加干扰线
		return image;
	}
	
	public String getText() {
		return text;
	}
	
	//保存图片到指定的输出流
	public static void output(BufferedImage image,OutputStream out) throws IOException {
		ImageIO.write(image, "JPEG", out);
	}

}
