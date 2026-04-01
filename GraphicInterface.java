import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicInterface extends JFrame implements ActionListener {
    private float xA, yA, xB, yB, xC, yC, xM, yM;
    private final JTextField txtxA;
    private final JTextField txtxB;
    private final JTextField txtxC;
    private final JTextField txtyA;
    private final JTextField txtyB;
    private final JTextField txtyC;
    private final JTextField txtxM;
    private final JTextField txtyM;
    private final JTextField output;
    private final JButton bGenerate;
    private final JButton bOutput;
    private final DrawPanel drawPanel;

    public GraphicInterface() {
        super("Problema 2");

        JPanel pControls = new JPanel();
        pControls.setLayout(new GridLayout(2, 1, 1, 1));
        pControls.setPreferredSize(new Dimension(900, 100));

        JPanel pTop = new JPanel();
        pTop.setLayout(new FlowLayout(FlowLayout.LEFT));
        pTop.add(new JLabel("Varfurile triunghiului A:"));
        txtxA = new JTextField(3);
        pTop.add(txtxA);
        txtyA = new JTextField(3);
        pTop.add(txtyA);

        pTop.add(new JLabel("B:"));
        txtxB = new JTextField(3);
        pTop.add(txtxB);
        txtyB = new JTextField(3);
        pTop.add(txtyB);

        pTop.add(new JLabel("C:"));
        txtxC = new JTextField(3);
        pTop.add(txtxC);
        txtyC = new JTextField(3);
        pTop.add(txtyC);

        bGenerate = new JButton("Genereaza triunghi");
        pTop.add(bGenerate);
        pControls.add(pTop);


        JPanel pMid = new JPanel();
        pMid.setLayout(new FlowLayout(FlowLayout.LEFT));
        pMid.add(new JLabel("Verifica pozitia punctului M:"));
        txtxM = new JTextField(3);
        pMid.add(txtxM);
        txtyM = new JTextField(3);
        pMid.add(txtyM);
        bOutput = new JButton("Output:");
        pMid.add(bOutput);
        output = new JTextField(50);
        pMid.add(output);
        pControls.add(pMid);

        add(pControls, BorderLayout.NORTH);

        drawPanel = new DrawPanel();
        drawPanel.setBackground(new Color(240, 243, 251));
        add(drawPanel);

        bGenerate.addActionListener(this);
        bOutput.addActionListener(this);

    }
    public void checkPointPosition() {
        float detMAB = determinantMatrix(xM, xA, xB, yM, yA, yB);
        float detMBC = determinantMatrix(xM, xB, xC, yM, yB, yC);
        float detMCA = determinantMatrix(xM, xC, xA, yM, yC, yA);

        if (detMAB > 0 && detMBC > 0 && detMCA > 0)
            output.setText("Interiorul triunghiului ABC");
        else {
            if (detMAB < 0 && detMBC > 0 && detMCA < 0)
                output.setText("Exteriorul triunghiului ABC - ZONA 2");
            else if(detMAB < 0 && detMBC < 0 && detMCA > 0)
                output.setText("Exteriorul triunghiului ABC - ZONA 3");
            else if(detMAB > 0 && detMBC < 0 && detMCA < 0)
                output.setText("Exteriorul triunghiului ABC - ZONA 4");
            else if(detMAB < 0 && detMBC > 0 && detMCA > 0)
                output.setText("Exteriorul triunghiului ABC - ZONA 5");
            else if(detMAB > 0 && detMBC > 0 && detMCA < 0)
                output.setText("Exteriorul triunghiului ABC - ZONA 6");
            else if(detMAB > 0 && detMBC < 0 && detMCA > 0)
                output.setText("Exteriorul triunghiului ABC - ZONA 7");
            else if(detMAB == 0 && detMBC > 0 && detMCA > 0)
                output.setText("Se afla pe FRONTIERA zonelor 1-5 a triunghiului ABC");
            else if(detMAB > 0 && detMBC > 0 && detMCA == 0)
                output.setText("Se afla pe FRONTIERA zonelor 1-6 a triunghiului ABC");
            else if(detMAB > 0 && detMBC == 0 && detMCA > 0)
                output.setText("Se afla pe FRONTIERA zonelor 1-7 a triunghiului ABC");
        }
    }
    private float determinantMatrix(float x1, float x2, float x3,
                                    float y1, float y2, float y3) {
        return x1*(y2 - y3) + x2*(y3 - y1) + x3*(y1 - y2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bGenerate) {
            try {
                xA = Float.parseFloat(txtxA.getText());
                xB = Float.parseFloat(txtxB.getText());
                xC = Float.parseFloat(txtxC.getText());
                yA = Float.parseFloat(txtyA.getText());
                yB = Float.parseFloat(txtyB.getText());
                yC = Float.parseFloat(txtyC.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Introduceti coordonate de tip real pentru varfurile triunghiului!");
                return;
            }
            drawPanel.setPoints(xA, xB, xC, yA, yB, yC);
        }

        if(e.getSource() == bOutput) {
            try {
                xM = Float.parseFloat(txtxM.getText());
                yM = Float.parseFloat(txtyM.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Introduceti coordonate de tip real pentru punctul M!");
                return;
            }
            drawPanel.setM(xM, yM);
            checkPointPosition();
        }
    }

    class DrawPanel extends JPanel {
        private float xA, xB, xC, yA, yB, yC, xM, yM;
        private boolean hasPoints = false;
        private boolean hasM = false;

        public void setPoints(float xa, float xb, float xc,float ya, float yb, float yc) {
            this.xA = xa;
            this.xB = xb;
            this.xC = xc;
            this.yA = ya;
            this.yB = yb;
            this.yC = yc;
            this.hasPoints = true;
            repaint();
        }

        public void setM(float xm, float ym){
            this.xM = xm;
            this.yM = ym;
            this.hasM = true;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D gCustom = (Graphics2D) g;

            int width = getWidth();
            int height = getHeight();

            gCustom.setStroke(new BasicStroke(1));
            gCustom.setColor(new Color(50,50,50));
            gCustom.drawLine(50, height - 50, width - 50, height - 50);
            gCustom.drawString("X", width - 40, height - 55);

            gCustom.setColor(new Color(50, 50, 50));
            gCustom.drawLine(50, height - 50, 50, 50);
            gCustom.drawString("Y", 35, 60);

            int x0 = 50;
            int y0 = height - 50;
            float scale = 50;        // pixeli per unitate

            if (hasPoints) {
                int xA_scr = (int) (x0 + xA * scale);
                int yA_scr = (int) (y0 - yA * scale);

                int xB_scr = (int) (x0 + xB * scale);
                int yB_scr = (int) (y0 - yB * scale);

                int xC_scr = (int) (x0 + xC * scale);
                int yC_scr = (int) (y0 - yC * scale);

                gCustom.setStroke(new BasicStroke(1));
                gCustom.setColor(new Color(239, 187, 243));
                drawInfiniteLine(gCustom, xA_scr, yA_scr, xB_scr, yB_scr, width, height);
                drawInfiniteLine(gCustom, xA_scr, yA_scr, xC_scr, yC_scr, width, height);
                drawInfiniteLine(gCustom, xB_scr, yB_scr, xC_scr, yC_scr, width, height);

                gCustom.setStroke(new BasicStroke(2));
                gCustom.setColor(new Color(216, 20, 155));
                gCustom.drawLine(xA_scr, yA_scr, xB_scr, yB_scr);

                gCustom.setStroke(new BasicStroke(2));
                gCustom.drawLine(xA_scr, yA_scr, xC_scr, yC_scr);

                gCustom.setStroke(new BasicStroke(2));
                gCustom.drawLine(xB_scr, yB_scr, xC_scr, yC_scr);

                gCustom.fillOval(xA_scr - 3, yA_scr - 3, 8, 8);
                gCustom.fillOval(xB_scr - 3, yB_scr - 3, 8, 8);
                gCustom.fillOval(xC_scr - 3, yC_scr - 3, 8, 8);
                gCustom.drawString("A", xA_scr - 15, yA_scr - 10);
                gCustom.drawString("B", xB_scr + 8, yB_scr + 5);
                gCustom.drawString("C", xC_scr - 5, yC_scr - 10);


            }

            if (hasM) {
                int xM_scr = (int) (x0 + xM * scale);
                int yM_scr = (int) (y0 - yM * scale);
                gCustom.setColor(new Color(98, 189, 36));
                gCustom.fillOval(xM_scr - 3, yM_scr - 3, 8, 8);
                gCustom.drawString("M", xM_scr - 15, yM_scr - 10);
            }
        }
        private void drawInfiniteLine(Graphics2D g, int x1, int y1, int x2, int y2, int panelWidth, int panelHeight) {
            float dx = x2 - x1;
            float dy = y2 - y1;

            if (dx == 0 && dy == 0) return;

            float tMax = 1000f;

            int xi1 = (int)(x1 - tMax*dx);
            int yi1 = (int)(y1 - tMax*dy);
            int xi2 = (int)(x1 + tMax*dx);
            int yi2 = (int)(y1 + tMax*dy);

            g.drawLine(xi1, yi1, xi2, yi2);
        }
    }
}