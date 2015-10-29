package cn.bjsxt.plane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;

import cn.bjsxt.util.Constant;
import cn.bjsxt.util.GameUtil;
import cn.bjsxt.util.MyFrame;

public class PlaneGameFrame extends MyFrame {
	Image bg = GameUtil.getImage("images/bg.jpg");
	Plane p = new Plane("images/plane.png",50,50);
	ArrayList  bulletList = new ArrayList();   
	Date startTime;
	Date endTime;
	
	Explode bao;
	
	public void paint(Graphics g){
		g.drawImage(bg, 0, 0, null);
		p.draw(g);
		for(int i=0;i<bulletList.size();i++){
			Bullet b = (Bullet) bulletList.get(i);
			b.draw(g);
			boolean peng = b.getRect().intersects(p.getRect());
			if(peng){
				p.setLive(false);  
				if(bao==null){
					endTime = new Date();
					bao = new Explode(p.x,p.y);
				}
				bao.draw(g);
				
				break;
			}
		}
		
		if(!p.isLive()){
			int period = (int)((endTime.getTime()-startTime.getTime())/1000);
			printInfo(g, "Time："+period+"Second", 20, 120, 260, Color.white);
			
			switch (period/10) {
			case 0:
			case 1:
				printInfo(g, "Bad", 50,100,200,Color.white);
				break;
			case 2:
				printInfo(g, "Good", 50,100,200,Color.white);
				break;
			case 3:
				printInfo(g, "Great", 50,100,200,Color.yellow);
				break;
			case 4:
				printInfo(g, "Wondeful", 50,100,200,Color.yellow);
				break;
			default:
				printInfo(g, "Winner", 50,100,200,Color.yellow);
				break;
			}
			
			
		}
		
		

		
	}
	
	
	
	public void printInfo(Graphics g,String str,int size,int x,int y,Color color){
		Color c = g.getColor();
		g.setColor(color);
		Font f = new Font("Times New Roma",Font.BOLD,size);
		g.setFont(f);
		g.drawString(str,x,y);
		g.setColor(c);

	}
	
	
	public static void main(String[] args) {
		new PlaneGameFrame().launchFrame();
	}
	
	
	public void launchFrame(){
		super.launchFrame();
		
		r(new KeyMonitor());
		
		
		for(int i=0;i<35;i++){
			Bullet b = new Bullet();
			bulletList.add(b);
		}
		
		startTime = new Date();
	}
	
	
	
	class KeyMonitor extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			p.addDirection(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			p.minusDirection(e);
		}
		
	}
	
	
	
}
