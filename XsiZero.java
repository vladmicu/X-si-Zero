import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class XsiZero{
	int[][] tabla = new int[3][3];
	int tura;	//1 is X, 2 is zero
	int lin;	//0 while game is being played, 1-8 on game over (marks winning line)
	
	public XsiZero(){
		reset();	
	}
	
	public void reset(){
	
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				tabla[i][j] = 0;
			}
		}
		tura = 1;
		lin = 0;	
	}
	
	public boolean check(int x, int y){
		int i;
		//checking line number x
		boolean chk = true;
		for(i=0; i<3; i++){
			if(tabla[x][i] != tabla[x][y])
				chk = false;
		}
		if(chk) {
			lin = x+1;
			return true;
		}
		//checking column number y
		chk = true;	
		for(i=0; i<3; i++){
			if(tabla[i][y] != tabla[x][y])
				chk = false;
		}
		if(chk) {
			lin = y+4;
			return true;
		}
		//checking the diagonals
		if(x==y){
			chk = true;
			for(i=0; i<3; i++){
				if(tabla[i][i] != tabla[x][y])
					chk = false;
			}
			if(chk) {
				lin = 7;
				return true;
			}
		}
		if(x==2-y){
			chk = true;
			for(i=0; i<3; i++){
				if(tabla[i][2-i] != tabla[x][y])
					chk = false;
			}
			if(chk) {
				lin = 8;
				return true;
			}	
		}
		//returns false if no winning lines pass through the square with coordinates (x,y) 
		return false;
	}
	
	public boolean act(int x, int y){
		if(tabla[x][y] == 0 && lin == 0){
			tabla[x][y] = tura;
			if(!check(x,y)){	
				tura = 2/tura;
			}
			return true;
		}
		//returns false if square is already filled or game is over
		return false;
	}
	
	public int get(int x, int y){
		return tabla[x][y];
	}
	
	public int getlin() {
		return lin;
	}
	
	public static void main(String[] args){
		
		XsiZero tabla = new XsiZero();
		
		JFrame f = new JFrame();
		
		JPanel p = new JPanel();
		JLabel[][] square = new JLabel[3][3];
		
		JButton res = new JButton("Reset");
		
		Dimension dimsq = new Dimension(100,100);	//size of a square
		
		p.setLayout(new GridLayout(3,3,10,10));
		p.setBackground(Color.black);
		
		//images for X and Zero
		ImageIcon ics = new ImageIcon("ics.png");
		ImageIcon zero = new ImageIcon("zero.png");
		
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				final int i2 = i;
				final int j2 = j;
				
				square[i][j] = new JLabel() {
						protected void paintComponent(Graphics g)
						{
							//this switch makes the graphics displayed match the board
							switch(tabla.get(i2,j2)) {
							case 0:
								g.setColor(Color.white);
								g.fillRect(0,0,100,100);
								break;
							case 1:
								g.drawImage(ics.getImage(), 0, 0, ics.getImageObserver());
								break;
							case 2:
								g.drawImage(zero.getImage(), 0, 0, zero.getImageObserver());
								break;
							}
						}
				};
				
				square[i][j].setPreferredSize(dimsq);
				
				square[i][j].addMouseListener(new MouseListener(){
					public void mouseClicked(MouseEvent e){
						//act on the board and repaint the square if changes were made
						if(tabla.act(i2, j2)) {
							square[i2][j2].repaint();
						}
					}
					public void mouseExited(MouseEvent e){
					}
					public void mouseEntered(MouseEvent e){
					}
					public void mousePressed(MouseEvent e){
					}
					public void mouseReleased(MouseEvent e){
					}
				});
				p.add(square[i][j]);
			}
		}
		
		//adding the functionality to the reset button
		res.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tabla.reset();
				p.repaint();
			}
		});		
		
		f.add(p, BorderLayout.CENTER);
		f.add(res, BorderLayout.PAGE_END);
		
		f.pack();
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		f.setVisible(true);
	}
}	
