import javax.swing.*;
import java.awt.event.*;
import java.util.*;
public class BETA {

  static int kingpositionC, kingpositionL; //keep track of where the king is.
  static int humanAsWhite=-1; //1=human as white , 0= human as black
  static int globalDepth = 4;
  static String chessBoard[][]={
          {"r","k","b","q","a","b","k","r"},
          {"p","p","p","p","p","p","p","p"},
          {" "," "," "," "," "," "," "," "},
          {" "," "," "," "," "," "," "," "},
          {" "," "," "," "," "," "," "," "},
          {" "," "," "," "," "," "," "," "},
          {"P","P","P","P","P","P","P","P"},
          {"R","K","B","Q","A","B","K","R"}};
    /*
    p/P = pawn
    r/R = rook
    k/K = knight
    b/B = bishop
    q/Q = queen
    a/A = king

    x1, y1, x2, y2, captured peice (how moves are registered)

    */

  public static void main(String[] args) {
    while(!"A".equals(chessBoard[kingpositionC/8][kingpositionC%8])){kingpositionC++;}
    while(!"a".equals(chessBoard[kingpositionL/8][kingpositionL%8])){kingpositionL++;}
    Object[] option={"Computer", "Human"};
    humanAsWhite=JOptionPane.showOptionDialog(null, "Who should play as white?", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
    JFrame f=new JFrame("Chess");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    UI ui = new UI();
    f.add(ui);
    f.setSize(500, 500);
    f.setVisible(true);
    if(humanAsWhite==0){
      makeMove(minimax(globalDepth, 1000000, -1000000, "", 0));
      flipboard();
      f.repaint();
    }
    for(int i=0;i<8;i++){
      System.out.println(Arrays.toString(chessBoard[i]));
    }
  }

  public static String randomMove(){
    Random random = new Random();
    String list =possibleMoves();
    int m;
    m = random.nextInt(list.length()/5);
    m*=5;
    String f = "";
    for(int i=0; i<=4; i++)
    {
      f+=list.charAt(m);
      m++;
    }
    return f;
  }

  public static String takeWhenPossible(){
    String list =possibleMoves();
    String f= "";
    for(int i=4; i<=list.length(); i+=5){
      if(('q') == (list.charAt(i)))
      {
        int p=i-4;
        for(int j=p; j<=i; j++)
        {
          f+=list.charAt(j);
        }
        return f;
      } else if(('k') == (list.charAt(i))){
        int p=i-4;
        for(int j=p; j<=i; j++)
        {
          f+=list.charAt(j);
        }
        return f;
      }else if(('p') == (list.charAt(i))){
        int p=i-4;
        for(int j=p; j<=i; j++)
        {
          f+=list.charAt(j);
        }
        return f;
      } else if(('r') == (list.charAt(i))){
        int p=i-4;
        for(int j=p; j<=i; j++)
        {
          f+=list.charAt(j);
        }
        return f;
      }else if(('b') == (list.charAt(i))){
        int p=i-4;
        for(int j=p; j<=i; j++)
        {
          f+=list.charAt(j);
        }
        return f;
      }else if(('P') == (list.charAt(i))){
        int p=i-4;
        for(int j=p; j<=i; j++)
        {
          f+=list.charAt(j);
        }
        return f;
      }
    }
    Random random = new Random();
    int m;
    m = random.nextInt(list.length()/5);
    m*=5;
    for(int i=0; i<=4; i++)
    {
      f+=list.charAt(m);
      m++;
    }
    return f;
  }

  public static String minimax(int depth, int beta, int alpha, String move, int player){
    //return in the form of 1234b#######
    String list=possibleMoves();
    if (depth==0 || list.length()==0) {return move+(Rating.rating(list.length(), depth)*(player*2-1));}
    list=sortMoves(list);
    player=1-player;//either 1 or 0
    for (int i=0;i<list.length();i+=5) {
      makeMove(list.substring(i,i+5));
      flipboard();
      String returnString=minimax(depth-1, beta, alpha, list.substring(i,i+5), player);
      int value=Integer.valueOf(returnString.substring(5));
      flipboard();
      undoMove(list.substring(i,i+5));
      if (player==0) {
        if (value<=beta) {beta=value; if (depth==globalDepth) {move=returnString.substring(0,5);}}
      } else {
        if (value>alpha) {alpha=value; if (depth==globalDepth) {move=returnString.substring(0,5);}}
      }
      if (alpha>=beta) {
        if (player==0) {return move+beta;} else {return move+alpha;}
      }
    }
    if (player==0) {return move+beta;} else {return move+alpha;}
  }

  public static void flipboard(){
    String temp;
    for(int i=0; i<32; i++){
      int r=i/8, c=i%8;
      if(Character.isUpperCase(chessBoard[r][c].charAt(0))){
        temp=chessBoard[r][c].toLowerCase();
      } else{
        temp=chessBoard[r][c].toUpperCase();
      }
      if(Character.isUpperCase(chessBoard[7-r][7-c].charAt(0))){
        chessBoard[r][c]=chessBoard[7-r][7-c].toLowerCase();
      } else{
        chessBoard[r][c]=chessBoard[7-r][7-c].toUpperCase();
      }
      chessBoard[7-r][7-c]=temp;
    }
    int kingTemp=kingpositionC;
    kingpositionC=63-kingpositionL;
    kingpositionL=63-kingTemp;
  }

  public static void makeMove(String move){
    if(move.charAt(4) != 'P'){
      //x1, y1, x2, y2, captured peice

      chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))]=chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))];
      chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]=" ";
      if("A".equals(chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))])){
        kingpositionC=8*Character.getNumericValue(move.charAt(2))+Character.getNumericValue(move.charAt(3));
        possibleMoves();
      }
    } else{ //if pawn promotion
      //column1, column2, captured piece, new piece, P
      chessBoard[1][Character.getNumericValue(move.charAt(0))]=" ";
      chessBoard[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(0));
    }
  }

  public static void undoMove(String move){
    if(move.charAt(4) != 'P'){
      //x1, y1, x2, y2, captured peice
      chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]=chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))];
      chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))]=String.valueOf(move.charAt(4));
      if("A".equals(chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))])){
        kingpositionC=8*Character.getNumericValue(move.charAt(0))+Character.getNumericValue(move.charAt(1));
      }
    } else{ //if pawn promotion
      //column1, column2, captured piece, new piece, P
      chessBoard[0][Character.getNumericValue(move.charAt(1))]="P";
      chessBoard[1][Character.getNumericValue(move.charAt(0))]=String.valueOf(move.charAt(2));
    }
  }

  public static String possibleMoves(){
    String list="";
    for(int i = 0; i<64;i++){
      switch(chessBoard[i/8][i%8]){
        case "P": list+=possibleP(i);
          break;
        case "R": list+=possibleR(i);
          break;
        case "K": list+=possibleK(i);
          break;
        case "B": list+=possibleB(i);
          break;
        case "Q": list+=possibleQ(i);
          break;
        case "A": list+=possibleA(i);
          break;

      }
    }
    return list; //x1, y1, x2, yx, captured peice
  }

  public static String possibleP(int i){
    String list="", oldPiece;
    int r=i/8, c= i%8;
    for(int j=-1; j<=1; j+=2){
      try{//capture
        if(Character.isLowerCase(chessBoard[r-1][c+j].charAt(0)) && i>=16){
          oldPiece=chessBoard[r-1][c+j];
          chessBoard[r][c]=" ";
          chessBoard[r-1][c+j]="P";
          if(KingSafe()){
            list=list+r+c+(r-1)+(c+j)+oldPiece;
          }
          chessBoard[r][c]="P";
          chessBoard[r-1][c+j]=oldPiece;
        }
      }catch (Exception e){}
      try{//promotion by capture
        if(Character.isLowerCase(chessBoard[r-1][c+j].charAt(0)) && i<=16){
          String [] promo = {"Q", "R", "B", "K"};
          oldPiece=chessBoard[r-1][c+j];
          chessBoard[r][c]=" ";
          chessBoard[r-1][c+j]=promo[0];
          if(KingSafe()){
            //column1, column2, captured piece, new piece, P
            list=list+c+(c+j)+oldPiece+promo[0]+"P";
          }
          chessBoard[r][c]="P";
          chessBoard[r-1][c+j]=oldPiece;

        }
      }catch (Exception e){}
    }
    try{//move one space
      if(" ".equals(chessBoard[r-1][c]) && i>=16){
        oldPiece=chessBoard[r-1][c];
        chessBoard[r][c]=" ";
        chessBoard[r-1][c]="P";
        if(KingSafe()){
          list=list+r+c+(r-1)+c+oldPiece;
        }
        chessBoard[r][c]="P";
        chessBoard[r-1][c]=oldPiece;
      }
    }catch (Exception e){}
    try{//move two spaces
      if(" ".equals(chessBoard[r-1][c]) && " ".equals(chessBoard[r-2][c]) && i>=48){
        oldPiece=chessBoard[r-2][c];
        chessBoard[r][c]=" ";
        chessBoard[r-2][c]="P";
        if(KingSafe()){
          list=list+r+c+(r-2)+c+oldPiece;
        }
        chessBoard[r][c]="P";
        chessBoard[r-2][c]=oldPiece;
      }
    }catch (Exception e){}
    try{//move promotion
      if(" ".equals(chessBoard[r-1][c]) && i<=16){
        String [] promo = {"Q","R","B","K"};
        oldPiece=chessBoard[r-1][c];
        chessBoard[r][c]=" ";
        chessBoard[r-1][c]=promo[0];
        if(KingSafe()){
          //column1, column2, captured piece, new piece, P
          list=list+c+c+oldPiece+promo[0]+"P";
        }
        chessBoard[r][c]="P";
        chessBoard[r-1][c]=oldPiece;
      }
    }catch (Exception e){}

    return list;
  }

  public static String possibleR(int i){
    String list="", oldPiece;
    int r=i/8, c= i%8;
    int temp=1;
    for(int j=-1; j<=1; j+=2){
      try{
        while(" ".equals(chessBoard[r][c+temp*j])){
          oldPiece=chessBoard[r][c+temp*j];
          chessBoard[r][c]=" ";
          chessBoard[r][c+temp*j]="R";
          if(KingSafe()){
            list=list+r+c+r+(c+temp*j)+oldPiece;
          }
          chessBoard[r][c]="R";
          chessBoard[r][c+temp*j]=oldPiece;
          temp++;
        }
        if(Character.isLowerCase(chessBoard[r][c+temp*j].charAt(0))){
          oldPiece=chessBoard[r][c+temp*j];
          chessBoard[r][c]=" ";
          chessBoard[r][c+temp*j]="R";
          if(KingSafe()){
            list=list+r+c+r+(c+temp*j)+oldPiece;
          }
          chessBoard[r][c]="R";
          chessBoard[r][c+temp*j]=oldPiece;
        }
      }catch (Exception e){}
      temp=1;
      try{
        while(" ".equals(chessBoard[r+temp*j][c])){
          oldPiece=chessBoard[r+temp*j][c];
          chessBoard[r][c]=" ";
          chessBoard[r+temp*j][c]="R";
          if(KingSafe()){
            list=list+r+c+(r+temp*j)+c+oldPiece;
          }
          chessBoard[r][c]="R";
          chessBoard[r+temp*j][c]=oldPiece;
          temp++;
        }
        if(Character.isLowerCase(chessBoard[r+temp*j][c].charAt(0))){
          oldPiece=chessBoard[r+temp*j][c];
          chessBoard[r][c]=" ";
          chessBoard[r+temp*j][c]="R";
          if(KingSafe()){
            list=list+r+c+(r+temp*j)+c+oldPiece;
          }
          chessBoard[r][c]="R";
          chessBoard[r+temp*j][c]=oldPiece;
        }
      }catch (Exception e){}
      temp=1;
    }
    return list;
  }

  public static String possibleK(int i){
    String list="", oldPiece;
    int r=i/8, c= i%8;
    for(int j=-1; j<=1; j+=2){
      for(int k=-1; k<=1; k+=2){
        try{
          if(Character.isLowerCase(chessBoard[r+2*j][c+k].charAt(0)) || " ".equals(chessBoard[r+2*j][c+k])){
            oldPiece=chessBoard[r+2*j][c+k];
            chessBoard[r][c]=" ";
            chessBoard[r+2*j][c+k]="K";
            if(KingSafe()){
              list=list+r+c+(r+2*j)+(c+k)+oldPiece;
            }
            chessBoard[r][c]="K";
            chessBoard[r+2*j][c+k]=oldPiece;
          }
        }catch (Exception e){}
        try{
          if(Character.isLowerCase(chessBoard[r+j][c+2*k].charAt(0)) || " ".equals(chessBoard[r+j][c+2*k])){
            oldPiece=chessBoard[r+j][c+2*k];
            chessBoard[r][c]=" ";
            chessBoard[r+j][c+2*k]="K";
            if(KingSafe()){
              list=list+r+c+(r+j)+(c+2*k)+oldPiece;
            }
            chessBoard[r][c]="K";
            chessBoard[r+j][c+2*k]=oldPiece;
          }
        }catch (Exception e){}
      }
    }
    return list;
  }

  public static String possibleB(int i){
    String list="", oldPiece;
    int r=i/8, c= i%8;
    int temp=1;
    for(int j=-1; j<=1; j+=2){
      for(int k=-1; k<=1; k+=2){
        try{
          while(" ".equals(chessBoard[r+temp*j][c+temp*k])){
            oldPiece=chessBoard[r+temp*j][c+temp*k];
            chessBoard[r][c]=" ";
            chessBoard[r+temp*j][c+temp*k]="B";
            if(KingSafe()){
              list=list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
            }
            chessBoard[r][c]="B";
            chessBoard[r+temp*j][c+temp*k]=oldPiece;
            temp++;
          }
          if(Character.isLowerCase(chessBoard[r+temp*j][c+temp*k].charAt(0))){
            oldPiece=chessBoard[r+temp*j][c+temp*k];
            chessBoard[r][c]=" ";
            chessBoard[r+temp*j][c+temp*k]="B";
            if(KingSafe()){
              list=list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
            }
            chessBoard[r][c]="B";
            chessBoard[r+temp*j][c+temp*k]=oldPiece;
          }
        }catch (Exception e){}
        temp =1;
      }
    }
    return list;
  }

  public static String possibleQ(int i){
    String list="", oldPiece;
    int r=i/8, c= i%8;
    int temp=1;
    for(int j=-1; j<=1; j++){
      for(int k=-1; k<=1; k++){
        if(j!= 0 || k!=0){
          try{
            while(" ".equals(chessBoard[r+temp*j][c+temp*k])){
              oldPiece=chessBoard[r+temp*j][c+temp*k];
              chessBoard[r][c]=" ";
              chessBoard[r+temp*j][c+temp*k]="Q";
              if(KingSafe()){
                list=list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
              }
              chessBoard[r][c]="Q";
              chessBoard[r+temp*j][c+temp*k]=oldPiece;
              temp++;
            }
            if(Character.isLowerCase(chessBoard[r+temp*j][c+temp*k].charAt(0))){
              oldPiece=chessBoard[r+temp*j][c+temp*k];
              chessBoard[r][c]=" ";
              chessBoard[r+temp*j][c+temp*k]="Q";
              if(KingSafe()){
                list=list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
              }
              chessBoard[r][c]="Q";
              chessBoard[r+temp*j][c+temp*k]=oldPiece;
            }
          }catch (Exception e){}
          temp =1;
        }
      }
    }
    return list;
  }

  public static String possibleA(int i){
    String list="", oldPiece;
    int r=i/8, c= i%8;
    for (int j = 0; j<9; j++){
      if(j != 4){
        try{
          if(Character.isLowerCase(chessBoard[r-1+j/3][c-1+j%3].charAt(0)) || " ".equals(chessBoard[r-1+j/3][c-1+j%3])){
            oldPiece=chessBoard[r-1+j/3][c-1+j%3];
            chessBoard[r][c] = " ";
            chessBoard[r-1+j/3][c-1+j%3]="A";
            int kingTemp = kingpositionC;
            kingpositionC=i+(j/3)*8+j%3-9;
            if(KingSafe()){
              list=list+r+c+(r-1+j/3)+(c-1+j%3)+oldPiece;
            }
            chessBoard[r][c] = "A";
            chessBoard[r-1+j/3][c-1+j%3]=oldPiece;
            kingpositionC=kingTemp;
          }
        } catch (Exception e){}
      }
    }
    //add castle later
    return list;
  }

  public static String possibleCastle(int i, String moves){
    return "";
  }

  public static String sortMoves(String list) {
    int[] score=new int [list.length()/5];
    for (int i=0;i<list.length();i+=5) {
      makeMove(list.substring(i, i+5));
      score[i/5]=-Rating.rating(-1, 0);
      undoMove(list.substring(i, i+5));
    }
    String newListA="", newListB=list;
    for (int i=0;i<Math.min(6, list.length()/5);i++) {//first few moves only
      int max=-1000000, maxLocation=0;
      for (int j=0;j<list.length()/5;j++) {
        if (score[j]>max) {max=score[j]; maxLocation=j;}
      }
      score[maxLocation]=-1000000;
      newListA+=list.substring(maxLocation*5,maxLocation*5+5);
      newListB=newListB.replace(list.substring(maxLocation*5,maxLocation*5+5), "");
    }
    return newListA+newListB;
  }

  public static boolean KingSafe(){
    int temp=1;
    //diagonal queen/bishop
    for(int j=-1; j<=1; j+=2){
      for(int k=-1; k<=1; k+=2){
        try{
          while(" ".equals(chessBoard[kingpositionC/8+temp*j][kingpositionC%8+temp*k])){temp++;}
          if("b".equals(chessBoard[kingpositionC/8+temp*j][kingpositionC%8+temp*k]) || "q".equals(chessBoard[kingpositionC/8+temp*j][kingpositionC%8+temp*k]))
          {
            return false;
          }
        }catch (Exception e){}
        temp =1;
      }
    }
    //horizontal queen/rook
    for(int i=-1; i<=1; i+=2){
      try{
        while(" ".equals(chessBoard[kingpositionC/8][kingpositionC%8+temp*i])){temp++;}
        if("r".equals(chessBoard[kingpositionC/8][kingpositionC%8+temp*i]) || "q".equals(chessBoard[kingpositionC/8][kingpositionC%8+temp*i])){
          return false;
        }
      }catch (Exception e){}
      temp=1;
    }
    //vertical rook/queen
    for(int i=-1; i<=1; i+=2){
      try{
        while(" ".equals(chessBoard[kingpositionC/8+temp*i][kingpositionC%8])){temp++;}
        if("r".equals(chessBoard[kingpositionC/8+temp*i][kingpositionC%8]) || "q".equals(chessBoard[kingpositionC/8+temp*i][kingpositionC%8])){
          return false;
        }
      }catch (Exception e){}
      temp=1;
    }
    //check kinight
    for(int j=-1; j<=1; j+=2){
      for(int k=-1; k<=1; k+=2){
        try{
          if("k".equals(chessBoard[kingpositionC/8+2*j][kingpositionC%8+k]))
          {
            return false;
          }
        }catch (Exception e){}
        //not in the same since if there is a ilegal move try will stop execution and not analise the other possible knight
        try{
          if("k".equals(chessBoard[kingpositionC/8+j][kingpositionC%8+2*k]))
          {
            return false;
          }
        }catch (Exception e){}
      }
    }
    //check pawn
    if(kingpositionC>=16){
      for(int i=-1; i<=1; i+=2){
        try{
          if("p".equals(chessBoard[kingpositionC/8-1][kingpositionC%8+i]))
          {
            return false;
          }
        }catch (Exception e){}
      }
    }
    //check king
    for(int j=-1; j<=1; j++){
      for(int k=-1; k<=1; k++){
        try{
          if(j != 0 || k != 0){
            if("a".equals(chessBoard[kingpositionC/8+j][kingpositionC%8+k]))
            {
              return false;
            }
          }
        }catch (Exception e){}
      }
    }
    return true;
  }

}
