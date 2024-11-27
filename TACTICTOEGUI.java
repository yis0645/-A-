import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
@SuppressWarnings("serial")
class TACTICTOEPanel extends JPanel {
	
    private JLabel TTTJLabel;
    private JButton TTTStart,TTTEx;
    

    public TACTICTOEPanel(JFrame TTT) {
    	setLayout(null);
    	setPreferredSize(new Dimension(750,750));
        setBackground(Color.white);
        
        TTTJLabel = new JLabel("TAC TIC TOE",SwingConstants.CENTER);
        TTTJLabel.setLocation(175,200);
        TTTJLabel.setSize(400,50);
        TTTJLabel.setFont(new Font(" ", Font.BOLD,40));
        add(TTTJLabel);
        
        TTTStart = new JButton("게임 시작");
        TTTStart.setBounds(325,375,100,35);
        TTTStart.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent event) {
        		
                TTT.getContentPane().removeAll();
                TTT.getContentPane().add(new TACTICTOEGAMEPANEL(TTT));
                TTT.revalidate();
                TTT.repaint();	
        	}
        });
        add(TTTStart);
     
        TTTEx = new JButton("게임 설명");
        TTTEx.setBounds(325,450,100,35);
        TTTEx.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent event) {
        		
                TTT.getContentPane().removeAll();
                TTT.getContentPane().add(new TACTICTOEEX(TTT));
                TTT.revalidate();
                TTT.repaint();	
        	}
        });
        add(TTTEx);
 
    }
    
}
@SuppressWarnings("serial")
class TACTICTOEEX extends JPanel {
	
	public TACTICTOEEX(JFrame TTT) {
		setLayout(null);
		setPreferredSize(new Dimension(750,750));
		setBackground(Color.white);
		
		JLabel Ex1 = new JLabel("아직 문구 안정함");
		Ex1.setFont(new Font(" ", Font.PLAIN, 15));
		Ex1.setBounds(100,500,300,100);
		add(Ex1);
		
		JButton Back = new JButton("돌아가기");
		Back.setBounds(50, 650, 100, 35);
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TTT.getContentPane().removeAll();
                TTT.getContentPane().add(new TACTICTOEPanel(TTT));
                TTT.revalidate();
                TTT.repaint();
            }
        });
        add(Back);
	}
	
}

@SuppressWarnings("serial")
class TACTICTOEGAMEPANEL extends JPanel {
    private final int[][] board;
    private final ArrayList<POINT> points;
    private int count = 0;
    private boolean GameEnd = false;
    private JLabel Player1;
    private JLabel Player2;
    final int wid = 160;
    final int hei = 160;
    
    public TACTICTOEGAMEPANEL(JFrame TTT) {
        board = new int[3][3];
        points = new ArrayList<>();
        setLayout(null);
        setPreferredSize(new Dimension(750, 750));
        setBackground(Color.white);

        Player1 = new JLabel("플레이어1");
        Player2 = new JLabel("플레이어2");
        Player1.setFont(new Font(" ", Font.BOLD, 20));
        Player2.setFont(new Font(" ", Font.BOLD, 20));
        Player1.setBounds(135, 50, 100, 100);
        Player2.setBounds(525, 50, 100, 100);

        Color();
        add(Player1);
        add(Player2);

        JPanel boardPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
            	
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D)g;
                g2.setStroke(new BasicStroke(4));
                g.setColor(Color.BLACK);
                
                g.drawLine(0, 160, 480, 160);
                g.drawLine(0, 320, 480, 320);
                g.drawLine(160, 0, 160, 480);
                g.drawLine(320, 0, 320, 480);
                g.drawLine(0, 1, 479, 1);
                g.drawLine(0, 0, 0, 480);
                g.drawLine(479, 0, 479, 479);
                g.drawLine(0, 479, 479, 479);
                g2.setStroke(new BasicStroke(1));
                
                for (POINT POINTS : points) {
                    Point point = POINTS.point;
                    Color color = POINTS.color;
                    int X = (point.x / wid) * wid;
                    int Y = (point.y / hei) * hei;
                    
                    g.setColor(color);
                    
                    g.fillRect(X+2, Y+2, 157, 157);
                }
            }
        };

        boardPanel.setBounds(135, 150, 480, 480);
        boardPanel.setBackground(Color.white);
        boardPanel.addMouseListener(new TTTMouseListener());
        add(boardPanel);

        JButton Back = new JButton("돌아가기");
        Back.setBounds(50, 650, 100, 35);
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TTT.getContentPane().removeAll();
                TTT.getContentPane().add(new TACTICTOEPanel(TTT));
                TTT.revalidate();
                TTT.repaint();
            }
        });
        add(Back);
    }

    private void Color() {
        if (count % 2 == 0) {
            Player1.setForeground(Color.RED);
            Player2.setForeground(Color.BLACK);    
        }
        
        else {
            Player1.setForeground(Color.BLACK);
            Player2.setForeground(Color.RED);
        }
    }

    private boolean WIN (int player) {
        for (int i = 0; i <= 2; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) || (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }
        
        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) || (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
            return true;
        }

        return false;
    }

    private void endGame(String m) {
        GameEnd = true;
        JOptionPane.showMessageDialog(this, m);
    }

    private class POINT {
        public Point point;
        public Color color;
        
        public POINT(Point point, Color color) {
            this.point = point;
            this.color = color;
        }
    }

    private class TTTMouseListener implements MouseListener {
        @Override
        public void mousePressed(MouseEvent e) {

            if (GameEnd) return;
            

            int column = e.getX() / wid;
            int row = e.getY() / hei;
            if (board[row][column] != 0) return;
            
            int player = (count % 2 == 0) ? 1 : 2;
            Color color = (player == 1) ? Color.RED : Color.BLUE;
            board[row][column] = player;
            points.add(new POINT(new Point(e.getX(), e.getY()), color));

            if (points.size() > 6) {
            	POINT POINTS = points.get(0);
                int Col = POINTS.point.x / wid;
                int Row = POINTS.point.y / hei;
                board[Row][Col] = 0;
                points.remove(0);
            }
            
            if (count == 50) {
                endGame("무승부입니다");
                return;
            }
            
            count++;
            Color();
            repaint();
            
            if (WIN(player)) {
                endGame("플레이어 " + player + "의 승리 입니다");
                return;
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
}

public class TACTICTOEGUI {
    public static void main(String[] args) {
    	
        JFrame TTT = new JFrame("TAC TIC TOE");
        TTT.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TTT.getContentPane().add(new TACTICTOEPanel(TTT));
        TTT.pack();
        TTT.setVisible(true);
    }
}
