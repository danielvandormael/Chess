import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class UI extends JPanel implements MouseListener, MouseMotionListener{
    static int mouseX, mouseY, newMouseX, newMouseY;
    static int squareSize= 32;
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.LIGHT_GRAY);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        Image chesspieces;
        chesspieces =new ImageIcon("src/chesspieces.png").getImage();
        for(int i=0;i<64;i+=2){
            g.setColor(new Color(255,200,100));
            g.fillRect((i%8+(i/8)%2)*squareSize, (i/8)*squareSize, squareSize, squareSize);
            g.setColor(new Color(150,50,30));
            g.fillRect(((i+1)%8-((i+1)/8)%2)*squareSize, ((i+1)/8)*squareSize, squareSize, squareSize);
        }
        if(BETA.humanAsWhite==1){
            for(int j=0;j<64;j++){
                int k =-1, l=-1;
                switch(BETA.chessBoard[j/8][j%8]){
                    case "P": k=5; l=0;
                        break;
                    case "p": k=5; l=1;
                        break;
                    case "R": k=4; l=0;
                        break;
                    case "r": k=4; l=1;
                        break;
                    case "K": k=3; l=0;
                        break;
                    case "k": k=3; l=1;
                        break;
                    case "B": k=2; l=0;
                        break;
                    case "b": k=2; l=1;
                        break;
                    case "Q": k=1; l=0;
                        break;
                    case "q": k=1; l=1;
                        break;
                    case "A": k=0; l=0;
                        break;
                    case "a": k=0; l=1;
                        break;
                }
                if(k !=-1 && l !=-1){
                    g.drawImage(chesspieces, (j%8)*squareSize, (j/8)*squareSize, (j%8+1)*squareSize, (j/8+1)*squareSize, k*200, l*200, (k+1)*200, (l+1)*200, this);
                }
            }
        }else{ //makes pieces black in user side
            for(int j=0;j<64;j++){
                int k =-1, l=-1;
                switch(BETA.chessBoard[j/8][j%8]){
                    case "P": k=5; l=1;
                        break;
                    case "p": k=5; l=0;
                        break;
                    case "R": k=4; l=1;
                        break;
                    case "r": k=4; l=0;
                        break;
                    case "K": k=3; l=1;
                        break;
                    case "k": k=3; l=0;
                        break;
                    case "B": k=2; l=1;
                        break;
                    case "b": k=2; l=0;
                        break;
                    case "Q": k=1; l=1;
                        break;
                    case "q": k=1; l=0;
                        break;
                    case "A": k=0; l=1;
                        break;
                    case "a": k=0; l=0;
                        break;
                }
                if(k !=-1 && l !=-1){
                    g.drawImage(chesspieces, (j%8)*squareSize, (j/8)*squareSize, (j%8+1)*squareSize, (j/8+1)*squareSize, k*200, l*200, (k+1)*200, (l+1)*200, this);
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getX()<8*squareSize && e.getY()<8*squareSize){
            mouseX=e.getX();
            mouseY=e.getY();
            repaint();
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getX()<8*squareSize &&e.getY()<8*squareSize) {
            //if inside the board
            newMouseX=e.getX();
            newMouseY=e.getY();
            if (e.getButton()==MouseEvent.BUTTON1) {
                String dragMove;
                if (newMouseY/squareSize==0 && mouseY/squareSize==1 && "P".equals(BETA.chessBoard[mouseY/squareSize][mouseX/squareSize])) {
                    //pawn promotion
                    dragMove=""+mouseX/squareSize+newMouseX/squareSize+BETA.chessBoard[newMouseY/squareSize][newMouseX/squareSize]+"QP";
                } else {
                    //regular move
                    dragMove=""+mouseY/squareSize+mouseX/squareSize+newMouseY/squareSize+newMouseX/squareSize+BETA.chessBoard[newMouseY/squareSize][newMouseX/squareSize];
                }
                String userPosibilities=BETA.possibleMoves();
                if (userPosibilities.replaceAll(dragMove, "").length()<userPosibilities.length()) {
                    //if valid move
                    BETA.makeMove(dragMove);
                    BETA.flipboard();
                    BETA.makeMove(BETA.minimax(BETA.globalDepth, 1000000, -1000000, "", 0));
                    BETA.flipboard();
                    repaint();
                }
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

}
