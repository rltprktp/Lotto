import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Main extends JFrame {

    // 유저의 로또 번호
    ArrayList<Integer> lotto = new ArrayList<Integer>();
    // 당첨 로또 번호
    ArrayList<Integer> lotto_ran = new ArrayList<Integer>();
    
    private ImageIcon icon;
    private ImageIcon changeIcon;
    private Image img;
    private Image changeImg;

    int rank = 0; // 로또 등수
    int a = 0;
    int bounsNumber = 0; // 보너스 번호
    int winNumber = 0; // 당첨 번호 갯수
    int winBouns = 0; // 당첨 보너스 번호 갯수
    String message = "";

    JButton[] btn_45 = new JButton[45];

    JButton btn_reset = new JButton("초기화");
    JButton btn_auto = new JButton("자동선택");
    JButton btn_check = new JButton("확인");

    // 당첨 번호 확인을 위한 메서드
    public void lottoCheck() {
        // 당첨 번호 체크
        for(int i=0; i<6; i++) {
            for(int j=0; j<6; j++) {
                if(lotto_ran.get(i) == lotto.get(j)) {
                    winNumber++;
                }
            }
        }
        // 당첨 보너스 번호 체크
        for(int i=0; i<6; i++) {
            if(bounsNumber == lotto.get(i)) {
                winBouns++;
            }
        }

        if(winNumber == 6) {
            message = "축하합니다. 1등입니다. 당첨금 20억!";
            rank = 1;
        } else if(winNumber == 5 && winBouns == 1) {
            message = "축하합니다. 2등입니다. 당첨금 5천만원!";
            rank = 2;
        } else if(winNumber == 5) {
            message = "축하합니다. 3등입니다. 당첨금 1백만원";
            rank = 3;
        } else if(winNumber == 4) {
            message = "축하합니다. 4등입니다. 당첨금 5만원";
            rank = 4;
        } else if (winNumber == 3) {
            message = "축하합니다. 5등입니다. 당첨금 5천원";
            rank = 5;
        }

    }
    
    public void imgInsert(int i, JButton[] btn) {
        icon = new ImageIcon("img/"+ (i + 1) + ".jpg");
        img = icon.getImage();
        changeImg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        changeIcon = new ImageIcon(changeImg);
        btn[i] = new JButton(changeIcon);
    }

    public void init() {
        // 당첨 번호
        for (int i = 0; i < 7; i++) {
            int num = ((int) (Math.random() * 45) + 1);
            lotto_ran.add(num);
            for (int j = 0; j < i; j++) {
                if (lotto_ran.get(i) == lotto_ran.get(j)) {
                    i--;
                    break;
                }
            }
        } // for end
        bounsNumber = lotto_ran.get(6);
        lotto_ran.remove(6);
        Collections.sort(lotto_ran);

        // 로또 번호와 당첨 번호 작동이 잘 되는지 확인하기 위한 로또 번호 확인 (삭제해도 무방)
        System.out.println(lotto_ran + "보너스: " + bounsNumber);

        // JButton에 숫자 이미지 삽입
        for (int i = 0; i < 45; i++) {
            int num = i;
            btn_45[i] = new JButton();
            imgInsert(i, btn_45);
            btn_45[i].setBorderPainted(false);
            btn_45[i].addActionListener((e) -> {
                btn_45[num].setEnabled(false);
                lotto.add(num+1);
            });
        }
        // "확인"을 누르고 로또 당첨이 되지 않았을 때
        btn_check.addActionListener((e) -> {
            lottoCheck();
            // 로또 당첨이 되지 않았을 때
            if(winNumber <= 2) {
                JOptionPane.showConfirmDialog(null, "아쉽게도 당첨되지 않았습니다.", "당첨 확인", JOptionPane.PLAIN_MESSAGE);
            // 로또 당첨이 되었을 때
            } else {
                JOptionPane.showConfirmDialog(null, message, "당첨 확인", JOptionPane.PLAIN_MESSAGE);
            }
            lotto.clear();
            winNumber=0;
            winBouns=0;
            message="";
        });

        // 자동선택
        btn_auto.addActionListener((e) -> {
            lotto.clear();
            for (int i = 0; i < 45; i++) {
                btn_45[i].setEnabled(true);
            }
            // 자동 선택 번호 생성
            for (int i = 0; i < 6; i++) {
                a = (int)(Math.random() * 45) + 1;
                lotto.add(a);
                for (int j = 0; j < i; j++) {
                    if (lotto.get(i) == lotto.get(j)) {
                        i--;
                        break;
                    }
                }
            }
            for (int i = 0; i < 6; i++) {
                btn_45[lotto.get(i)-1].setEnabled(false);
            }
            btn_auto.setEnabled(false);
        });

        // 초기화 버튼
        btn_reset.addActionListener((e) -> {
            for (int i = 0; i < 45; i++) {
                btn_45[i].setEnabled(true);
            }
            btn_auto.setEnabled(true);
        });
    }

    // 전체적인 UI 구성
    private void showFrame() {
        JPanel pnlNorth = new JPanel(new GridLayout(6, 7, 5, 5));
        for (int i = 0; i < 45; i++) {
            pnlNorth.add(btn_45[i]);
            btn_45[i].setPreferredSize(new Dimension(26, 26));
        }
        pnlNorth.setBackground(Color.WHITE);

        btn_reset.setPreferredSize(new Dimension(80, 26));
        btn_auto.setPreferredSize(new Dimension(90, 26));
        btn_reset.setBackground(Color.WHITE);
        btn_auto.setBackground(Color.WHITE);
        btn_check.setBackground(Color.WHITE);

        // "확인" 버튼 패널
        JPanel pnlSouth = new JPanel(new BorderLayout());
        pnlSouth.add(btn_check, BorderLayout.CENTER);
        pnlSouth.setBackground(Color.WHITE);

        // "초기화" 및 "자동선택" 버튼 패널
        JPanel pnlCenter = new JPanel();
        pnlCenter.add(btn_reset);
        pnlCenter.add(btn_auto);
        pnlCenter.setBackground(Color.WHITE);

        // 전체 패널
        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.add(pnlNorth, BorderLayout.NORTH);
        pnlMain.add(pnlCenter, BorderLayout.CENTER);
        pnlMain.add(pnlSouth, BorderLayout.SOUTH);

        add(pnlMain);

        setTitle("로또");
        pack(); // 패널 사이즈에 맞춰서 생성
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false); // 창을 고정
        setVisible(true);
    }

    public Main() {
        init();
        showFrame();
    }

    public static void main(String[] args) {
        new Main();
    }
}
