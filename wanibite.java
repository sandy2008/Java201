  import java.awt.*;
  import java.awt.event.*;
  import java.awt.image.*;
  import java.io.*;
  import java.util.*;
  import javax.imageio.*;
  import javax.swing.*;

  public class wanibite extends JPanel {
    Scene cur = new Scene1();
 //   Scene cur = new Scene5();
    long tm0 = System.currentTimeMillis();
    public wanibite() {
      setOpaque(false);
      addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent evt) {
          cur.press(evt.getX(), evt.getY());
        }
      });
      new javax.swing.Timer(30, new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          float tm = 0.001f*(System.currentTimeMillis()-tm0);
          cur.setTime(tm); repaint();
          if(cur.isEnded()) { 
            cur = cur.getNext(); tm0 = System.currentTimeMillis();
          }
        }
      }).start();
    }
    public void paintComponent(Graphics g) { cur.draw(g); }
    public static void main(String[] args) {
      JFrame app = new JFrame("Wani Bite");
      app.add(new wanibite());
      app.setSize(600, 400);
      app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      app.setVisible(true);
    }
    static class Scene {
      ArrayList<Figure> figs = new ArrayList<Figure>();
      ArrayList<Animation> anim = new ArrayList<Animation>();
      boolean ended = false;
      Scene next = null;

      public void draw(Graphics g) {
        for(Figure f: figs) { f.draw(g); }
      }
      public void setTime(float t) {
        for(Animation a: anim) { a.setTime(t); }
      }
      public void press(int x, int y) { }
      public boolean isEnded() { return ended; }
      public Scene getNext() { return next; }
    }
    static class Scene1 extends Scene {
      Rect r1 = new Rect(Color.YELLOW, 300, 210, 100, 30);
      Rect r2 = new Rect(Color.YELLOW, 300, 260, 100, 30);
      Rect r3 = new Rect(Color.YELLOW, 300, 310, 100, 30);
      public Scene1() {
        Picture menu =  new Picture("menu.png", 255, 150);
        Picture p1 = new Picture("menutopic.png", 300, 100);
        Picture menu1 = new Picture("menu1.png", 300, 210);
        Picture menu2 = new Picture("menu2.png", 300, 260);
        Picture menu3 = new Picture("menu3.png", 300, 310);
        
        figs.add(menu); figs.add(r1); figs.add(r2); figs.add(r3); figs.add(menu1); figs.add(menu2); figs.add(menu3); figs.add(p1); 
       anim.add(new ShakeMove(menu1,  300, 210, 3, 0));
       anim.add(new ShakeMove(menu2,  300, 260, 3, 0));
       anim.add(new ShakeMove(menu3,  300, 310, 3, 0));
        figs.add(new Text(20, 25, "Click",
                          new Font("serif", Font.BOLD, 18)));
      }
      public void press(int x, int y) {
        if(r1.hit(x, y)) {
          next = new Scene2(); ended = true; 
        } else if(r2.hit(x, y)) {
          next = new Scene4(); ended = true; 
        } else if(r3.hit(x, y)) {
          next = new Scene3(); ended = true; 
        }
      }
    }
    static class Scene2 extends Scene {
      public Scene2() {
      Picture tutorial1 = new Picture("tutorial1.png", 200, 80);
    Picture tutorial2 = new Picture("tutorial2.png", 200, 80);
        next = new Scene1();
        figs.add( new Picture("tutorial.png", 300, 200));
        figs.add(tutorial1);
        figs.add(tutorial2);
          anim.add(new ShakeMove(tutorial1, 270, 100, 13, 0));
        anim.add(new ZigzagMove(tutorial2, 1f, 380, 80, 370, 100));
      }
      public void press(int x, int y) { ended = true; }
      public void setTime(float t) {for(Animation a: anim) { a.setTime(t); }ended |= (t > 5); }
    }
    static class Scene3 extends Scene {
      public Scene3() {
        next = new Scene1();
        Figure exit =   new Picture("exit.png", 300, 200);
        figs.add(exit);
        anim.add(new ShakeMove(exit, 300, 200, 13, 13));
      }
      public void press(int x, int y) { System.exit(0); }
      public void setTime(float t) { for(Animation a: anim) { a.setTime(t); } if(t>=5) System.exit(0); }
    }
    
    static class Scene4 extends Scene {
      Font fn = new Font("serif", Font.BOLD, 18);
      Text t1 = new Text(20, 25, "Click", fn);
      Rect ha1 = new Rect(Color.WHITE, 190, 310, 20, 30);
      Rect ha2 = new Rect(Color.WHITE, 220, 320, 20, 30);
      Rect ha3 = new Rect(Color.WHITE, 250, 320, 20, 30);
      Rect ha4 = new Rect(Color.WHITE, 280, 320, 20, 30);
      Rect ha5 = new Rect(Color.WHITE, 310, 320, 20, 30);
      Rect ha6 = new Rect(Color.WHITE, 340, 310, 20, 30);
      Rect ha7 = new Rect(Color.WHITE, 370, 300, 20, 30);
      boolean ok = false;
      float curtime = 0f, endtime = 20f;
      Random random = new Random();
      int s = random.nextInt(7)%(7) + 1;
      public Scene4() {
         Picture game1 = new Picture("game1.png", 300, 200);
        next = new Scene5();
         figs.add(game1); figs.add(ha1);figs.add(ha2);
        figs.add(ha3);figs.add(ha4);figs.add(ha5);figs.add(ha6); figs.add(ha7);figs.add(t1);
      }
      public void press(int x, int y) {
        if(ha1.hit(x, y)) {
          if(s==1) ended = true;
          else ha1.setColor(Color.PINK);
        } else if(ha2.hit(x, y)) {
          if(s==2) ended = true;
          else ha2.setColor(Color.PINK);
        } else if(ha3.hit(x, y)) {
          if(s==3) ended = true;
          else ha3.setColor(Color.PINK);
        } else if(ha4.hit(x, y)) {
          if(s==4) ended = true;
          else ha4.setColor(Color.PINK);
        } else if(ha5.hit(x, y)) {
          if(s==5) ended = true;
          else ha5.setColor(Color.PINK);
        } else if(ha6.hit(x, y)) {
          if(s==6) ended = true;
          else ha6.setColor(Color.PINK);
        } else if(ha7.hit(x, y)) {
        if(s==7) ended = true;
          else ha7.setColor(Color.PINK);
        } 
      }
      public void setTime(float t) {
        super.setTime(t); curtime = t; ended |= (t > endtime);
      }
    }
    
    static class Scene5 extends Scene {
      public Scene5() {
        next = new Scene3();
        figs.add(  new Picture("game2.png", 300, 200));
      }
      public void press(int x, int y) { ended = true; }
      public void setTime(float t) { if(t>=5) ended = true; }
    }

    interface Figure {
      public void draw(Graphics g);
      public void moveTo(float x, float y);
      public void setColor(Color c);
    }
    interface Animation {
      public void setTime(float dt);
    }
    static abstract class SimpleFigure implements Figure {
      Color col;
      float xpos, ypos;
      public SimpleFigure(Color c, float x, float y) {
        col = c; xpos = x; ypos = y;
      }
      public void moveTo(float x, float y) { xpos = x; ypos = y; }
      public void setColor(Color c) { col = c; }
      public void draw(Graphics g) { g.setColor(col); }
    }
    static class Circle extends SimpleFigure {
      float rad;
      public Circle(Color c, float x, float y, float r) {
        super(c, x, y); rad = r;
      }
      public boolean hit(float x, float y) {
        return (xpos-x)*(xpos-x) + (ypos-y)*(ypos-y) <= rad*rad;
      }
      public void draw(Graphics g) {
        int x = (int)(xpos-rad), y = (int)(ypos-rad);
        super.draw(g); g.fillOval(x, y, (int)rad*2, (int)rad*2);
      }
    }
    static class Rect extends SimpleFigure {
      float width, height;
      public Rect(Color c, float x, float y, float w, float h) {
        super(c, x, y); width = w; height = h;
      }
      public boolean hit(float x, float y) {
        return xpos-width/2 <= x && x <= xpos+width/2 &&
               ypos-height/2 <= y && y <= ypos+height/2;
      }
      public void draw(Graphics g) {
        int x = (int)(xpos-width/2), y = (int)(ypos-height/2);
        super.draw(g); g.fillRect(x, y, (int)width, (int)height);
      }
    }
    static class Triangle extends SimpleFigure {
      float dx1, dy1, dx2, dy2;
      public Triangle(Color c, float x, float y,
                      float x1, float y1, float x2, float y2) {
        super(c, x, y); dx1 = x1-x; dy1 = y1-y; dx2 = x2-x; dy2 = y2-y;
      }
      public void draw(Graphics g) {
        int[] xs = {(int)xpos, (int)(xpos+dx1), (int)(xpos+dx2)};
        int[] ys = {(int)ypos, (int)(ypos+dy1), (int)(ypos+dy2)};
        super.draw(g); g.fillPolygon(xs, ys, 3);
      }
    }
    static class Text extends SimpleFigure {
      String txt;
      Font fn;
      public Text(int x, int y, String t, Font f) {
        super(Color.BLACK, x, y); txt = t; fn = f;
      }
      public void setText(String t) { txt = t; }
      public void draw(Graphics g) {
        super.draw(g); g.setFont(fn);
        g.drawString(txt, (int)xpos, (int)ypos);
      }
    }
    static class Picture extends SimpleFigure {
      BufferedImage img;
      int width, height;
      public Picture(String fname, int x, int y) {
        super(Color.WHITE, x, y);
        try {
          img = ImageIO.read(new File(fname));
        } catch(Exception ex) { }
        xpos = x; ypos = y;
        width = img.getWidth(); height = img.getHeight();
      }
      public void draw(Graphics g) {
        int x = (int)xpos-width/2, y = (int)ypos-height/2;
        g.drawImage(img, x, y, null);
      }
    }

    static class LinearMove implements Animation {
      Figure fig;
      float time1, xpos1, ypos1, time2, xpos2, ypos2;
      public LinearMove(Figure f, float t1, float x1, float y1,
                                  float t2, float x2, float y2) {
        time1 = t1; xpos1 = x1; ypos1 = y1;
        time2 = t2; xpos2 = x2; ypos2 = y2; fig = f;
      }
      public void setTime(float t) {
        if(t < time1 || time2 < t) { return; }
        float p = (time2-t)/(time2-time1), q = 1.0f - p;
        fig.moveTo(p*xpos1 + q*xpos2, p*ypos1 + q*ypos2);
      }
    }
    static class ColorTrans implements Animation {
      Figure fig;
      float time1, time2;
      int r1, g1, b1, a1, r2, g2, b2, a2;
      public ColorTrans(Figure f, float t1, Color c1, float t2, Color c2) {
        fig = f; time1 = t1; time2 = t2;
        r1 = c1.getRed(); g1 = c1.getGreen(); b1 = c1.getBlue(); a1 = c1.getAlpha();
        r2 = c2.getRed(); g2 = c2.getGreen(); b2 = c2.getBlue(); a2 = c2.getAlpha();
     }
      public void setTime(float t) {
        if(t < time1 || time2 < t) { return; }
        float p = (time2-t)/(time2-time1), q = 1.0f - p;
        fig.setColor(new Color((int)(p*r1+q*r2), (int)(p*g1+q*g2),
                               (int)(p*b1+q*b2), (int)(p*a1+q*a2)));
      }
    }
    static class ZigzagMove implements Animation {
      Figure fig;
      float time1, xpos1, ypos1, xpos2, ypos2;
      public ZigzagMove(Figure f, float t1,
                        float x1, float y1, float x2, float y2) {
        time1 = t1; xpos1 = x1; ypos1 = y1; xpos2 = x2; ypos2 = y2; fig = f;
      }
      public void setTime(float t) {
        float q = (t % time1) / time1, p = 1.0f - q;
        fig.moveTo(p*xpos1 + q*xpos2, p*ypos1 + q*ypos2);
      }
    }
    static class TimedAnimation implements Animation {
      Animation anim;
      float time1, time2;
      public TimedAnimation(Animation a, float t1, float t2) {
        anim = a; time1 = t1; time2 = t2;
      }
      public void setTime(float t) {
        if(t < time1 || time2 < t) { return; }
        anim.setTime(t - time1);
      }
    }
    static class TimedAppearance implements Figure, Animation {
      Figure fig;
      float time, time1, time2;
      public TimedAppearance(Figure f, float t1, float t2) {
        fig = f; time1 = t1; time2 = t2;
      }
      public void moveTo(float x, float y) { fig.moveTo(x, y); }
      public void setColor(Color c) { fig.setColor(c); }
      public void setTime(float t) { time = t; }
      public void draw(Graphics g) {
        if(time1 <= time && time <= time2) { fig.draw(g); }
      }
    }
    static class ShakeMove implements Animation {
      Figure fig;
      float xpos, ypos, width, height;
      public ShakeMove(Figure f, float x, float y, float w, float h) {
        fig = f; xpos =x; ypos = y; width = w; height = h;
      }
      public void setTime(float t) {
        fig.moveTo((int)(xpos+(Math.random()-0.5)*width),
                   (int)(ypos+(Math.random()-0.5)*height));
      }
    }
  }
