package com.goBang.model;
import AIService.AIServiceImpl.AIwork;
import checkBoardService.checkBoardServiceImpl.judgeServiceImpl;
import checkBoardService.judgeService;
import checkBoardService.size;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
public class AI implements size {
    char aiturn,playerturn;
    int depth=4;
    Board board;
    char[][] isavil = new char[row][column];
    evaluated ed;
    ArrayList<chessman> chessmanArrayList=new ArrayList<>();
    Point max=null;
    AI(Board board,char AI){
        this.board=board;
        aiturn=AI;
        if(aiturn=='1') playerturn='2';
        else playerturn='1';
        copy(isavil);
        ed=new evaluated();
    }
    public void gen(ArrayList<chessman> gen,char turn,int depth){
        AIwork aIwork=new AIwork();
        aIwork.Find(board);
        //print(board.chessboard);
        if(depth==this.depth){
            if (aIwork.Max(board, aiturn, playerturn) != null) {
                max = aIwork.Max(board, aiturn, playerturn);
                System.out.println(max.x+" "+max.y);
                return;
            }
        }
        aIwork.InitSCore(board, aiturn, playerturn);
        aIwork.InitSCore_2(board,playerturn,aiturn);//得到playersore数组；
        //System.out.println(aiturn);
        for (int i=0;i<row;i++){
            for (int j=0;j<column;j++){
                //if(turn==playerturn)System.out.print(aIwork.playerscore[i][j]+"   ");
                if(aIwork.AIscore[i][j]!=0){
                    if(turn==aiturn){
                        gen.add(new chessman(new Point(i, j), aIwork.AIscore[i][j]));
                    }else gen.add(new chessman(new Point(i, j), aIwork.playerscore[i][j]));//将合适的点加入评估得到的数组
                }
            }
           // System.out.println();
        }
    }
    public void Alpha_Beta(){
        int Alpha=Integer.MIN_VALUE,Beta=Integer.MAX_VALUE;
        MINMAX(depth,Alpha,Beta);
        if(max!=null) {
            setchess(max);
            return;
        }
        Collections.sort(chessmanArrayList);
        //print(chessmanArrayList);
        setchess(chessmanArrayList.get(0).point);
    }
    public int  MINMAX(int depth,int Alpha,int Beta) {
        int cot=0;//启发式评估函数排序后的取数顺序。
        Alpha=Integer.MIN_VALUE;
        Beta=Integer.MAX_VALUE;
        ArrayList<chessman> gen=new ArrayList<>();//启发式评估函数点的存储
        if(depth%2==0){
            gen(gen,aiturn,depth);
        }
        else gen(gen,playerturn,depth);//调用启发式评估函数
        if(max!=null) return 0;
        Collections.sort(gen);//排序
        //print(gen);
        Point p;//new point
        if(depth==0){
            //System.out.println(ed.evaluate(isavil,aiturn));
            return ed.evaluate(isavil, aiturn);
        }
        while(cot<gen.size()) {
            judgeService judgeService=new judgeServiceImpl();
            p=gen.get(cot).point;
            if (depth % 2 == 0) {
                makenextmove(board, aiturn, p);
            } else {
                makenextmove(board, playerturn, p);
            }
            copy(isavil);
           // print(isavil);
            var val=-MINMAX(depth-1,-Beta,-Alpha);
            if(depth==this.depth) {
                chessmanArrayList.add(new chessman(p,ed.evaluate(isavil, aiturn)));//叶子节点结束
               // print(chessmanArrayList);
            }
            unmakenextmove(board,p);
            copy(isavil);
            if(val>Alpha){
                if (val >=Beta) {
                    return Beta;
                }
                Alpha=val;
            }
            cot++;
        }
        return Alpha;
    }
    public void makenextmove(Board board,char turn,Point p){
        if (board.isolation[p.x][p.y]=='0'){
            board.isolation[p.x][p.y]=turn;
            board.chessboard[p.x][p.y]=turn;
        }
        //print(board.isolation);
    }
    public void unmakenextmove(Board board,Point p){
        board.isolation[p.x][p.y]='0';
        board.chessboard[p.x][p.y]='0';
    }
    public boolean over(Board board){
        return false;
    }
    public void setchess( Point AIp){
        board.isolation[AIp.x][AIp.y]=aiturn;
        board.chessboard[AIp.x][AIp.y]=aiturn;
    }
    public void print(ArrayList<chessman> chessmanArrayList){
        //System.out.println(chessmanArrayList.size());
        for(chessman c:chessmanArrayList){
            System.out.println(c.point.x+"  "+c.point.y+"   "+c.eval);
        }
    }
    public void print(char[][] isvail){
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                System.out.print(isvail[i][j]+"   ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public void copy(char[][] isavil){
        for (int i = 0; i < row; i++) {
            isavil[i] = board.isolation[i].clone();
        }
    }

}
