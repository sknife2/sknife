package com.goBang.model;
import checkBoardService.checkBoardServiceImpl.judgeServiceImpl;
import checkBoardService.judgeService;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class frameL implements checkBoardService.size, MouseListener {
    public Board f;
    public frameL(Board ft){
        this.f=ft;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int x=e.getX();
        int y=e.getY();
        if((x-20)%40>=20){
            f.countx=(x-20)/40+1;
        }
        else{
            f.countx=(x-20)/40;
        }
        if((y-20)%40>=20){
           f.county=(y-20)/40+1;
        }
        else{
            f.county=(y-20)/40;
        }
        char c;
        if (f.turn == 1) {
            c = '1';
        } else {
            c = '2';
        }
        if(f.countx>=15||f.county>=15){
            JOptionPane.showMessageDialog(null,"请点击正确的位置");
        }
        else if(f.isolation[f.county][f.countx]!='0'){
            JOptionPane.showMessageDialog(null, "此处已经有棋子了，请下在其它地方");
        }
        else {
            if(f.turn==1){
                f.isolation[f.county][f.countx]='1';
                f.turn++;
            }
            else{
                f.isolation[f.county][f.countx]='2';
                f.turn--;
            }
            f.left.repaint();
        }
        judgeService js = new judgeServiceImpl();
        //System.out.println(f.countx + "   " + f.county);
        // System.out.println(js.judge(f, f.countx, f.county, c));
        //返回值有4中情况：1为没有任何状况、2为禁手、3为输、4为赢
        switch (js.judge(f,f.county,f.countx, c)) {
            case 1:
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "黑方禁手,黑方输");
                f.clear();
                f.turn=1;
                break;
            case 3:
                if(c=='1') JOptionPane.showMessageDialog(null, "黑方输");
                else JOptionPane.showMessageDialog(null, "白方输");
                f.clear();
                f.turn=1;
                break;
            case 4:
                if(c=='1')JOptionPane.showMessageDialog(null, "黑方赢");
                else JOptionPane.showMessageDialog(null, "白方赢");
                f.clear();
                f.turn=1;
                break;
        }
        if (f.turn == 1) {
        } else if(f.turn==2) {
            f.turn--;
        }
        f.print();
    }
    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
