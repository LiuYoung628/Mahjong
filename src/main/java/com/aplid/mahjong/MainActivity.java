package com.aplid.mahjong;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.aplid.mahjong.MahjongUtil.getIndex;
import static com.aplid.mahjong.MahjongUtil.getOrder;
import static com.aplid.mahjong.MahjongUtil.order;
import static com.aplid.mahjong.MahjongUtil.ziOrder;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    String[] MAHJONG = new String[]{
            "1m", "2m", "3m", "4m", "5m", "6m", "7m", "8m", "9m",
            "1m", "2m", "3m", "4m", "5m", "6m", "7m", "8m", "9m",
            "1m", "2m", "3m", "4m", "5m", "6m", "7m", "8m", "9m",
            "1m", "2m", "3m", "4m", "5m", "6m", "7m", "8m", "9m",

            "1s", "2s", "3s", "4s", "5s", "6s", "7s", "8s", "9s",
            "1s", "2s", "3s", "4s", "5s", "6s", "7s", "8s", "9s",
            "1s", "2s", "3s", "4s", "5s", "6s", "7s", "8s", "9s",
            "1s", "2s", "3s", "4s", "5s", "6s", "7s", "8s", "9s",

            "1p", "2p", "3p", "4p", "5p", "6p", "7p", "8p", "9p",
            "1p", "2p", "3p", "4p", "5p", "6p", "7p", "8p", "9p",
            "1p", "2p", "3p", "4p", "5p", "6p", "7p", "8p", "9p",
            "1p", "2p", "3p", "4p", "5p", "6p", "7p", "8p", "9p",

            "1z", "2z", "3z", "4z", "5z", "6z", "7z",
            "1z", "2z", "3z", "4z", "5z", "6z", "7z",
            "1z", "2z", "3z", "4z", "5z", "6z", "7z",
            "1z", "2z", "3z", "4z", "5z", "6z", "7z",

    }; //所有牌

    List<String> mahjong = new ArrayList<>();

    List<String> mountain = new ArrayList<>();

    List<String> publicPool = new ArrayList<>();

    List<String> dongHand = new ArrayList<>();
    List<String> nanHand = new ArrayList<>();
    List<String> xiHand = new ArrayList<>();
    List<String> beiHand = new ArrayList<>();

    List<String> dongPool = new ArrayList<>();
    List<String> nanPool = new ArrayList<>();
    List<String> xiPool = new ArrayList<>();
    List<String> beiPool = new ArrayList<>();

    List<String> dongShow = new ArrayList<>();
    List<String> nanShow = new ArrayList<>();
    List<String> xiShow = new ArrayList<>();
    List<String> beiShow = new ArrayList<>();

    Button btDongGetNew,btRenew,btNanGetAndOut;
    TextView tvDongHand, tvPublicPool;
    RecyclerView rvDongHand,rvNanHand,rvDongMingPai;
    HandAdapter dongHandAdapter,dongMingPaiAdapter;

    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        startGame();

    }

    private void startGame() {

        randomMahjong();

        initPlayerHand();

        showPlayerHand();

        initMountain();

        initPublicPool();
    }

    private void showPlayerHand() {
        tvDongHand.setText(dongHand.toString());
        dongHandAdapter.update(dongHand);
    }

    private void initView() {
        btRenew = findViewById(R.id.bt_renew);
        btRenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanTable();
            }
        });

        btNanGetAndOut = findViewById(R.id.bt_nan_get_and_out);
        btNanGetAndOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nanGetAndOut();
            }
        });

        btDongGetNew = findViewById(R.id.bt_dong_get_new);
        tvDongHand = findViewById(R.id.tv_dong_hand);
        btDongGetNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dongGetNew();
            }
        });
        tvPublicPool = findViewById(R.id.tv_pulbic_pool);

        rvDongHand = findViewById(R.id.rv_dong_hand);
        rvDongHand.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        dongHandAdapter = new HandAdapter();
        dongHandAdapter.setOnItemClickLitener(new HandAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, "东家打出：" + dongHand.get(position));
                dongOutOne(position);
            }
        });
        rvDongHand.setAdapter(dongHandAdapter);


        rvDongMingPai = findViewById(R.id.rv_dong_mingpai);
        rvDongMingPai.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        dongMingPaiAdapter = new HandAdapter();
        rvDongMingPai.setAdapter(dongMingPaiAdapter);



    }

    /*南家摸切*/
    private void nanGetAndOut() {

        String currentPai = publicPool.subList(0, 1).get(0);

        dongMingPaiAdapter.add(currentPai);

        publicPool.subList(0, 1).clear();

        tvPublicPool.setText("牌池剩余：" + publicPool.size());

        dongCanEat(currentPai);

    }

    private void dongCanEat(String pai) {

    }

    /**
     * 东家打出一张
     *
     * @param position
     */
    private void dongOutOne(int position) {
        if (dongHand.size() == 14) {
            dongHand.remove(position);
            dongHandAdapter.update(dongHand);
        } else {
            Log.d(TAG, "不可出牌");
            shouToast("不可出牌");
        }

    }

    /**
     * 东家摸一张
     */
    private void dongGetNew() {

        if (dongHand.size() == 13) {
            dongHand.addAll(publicPool.subList(0, 1));

            dongHandAdapter.add(publicPool.subList(0, 1).get(0));

            publicPool.subList(0, 1).clear();

            tvPublicPool.setText("牌池剩余：" + publicPool.size());

            tvDongHand.setText(dongHand.toString());

            judgeIsWin(dongHand);

            Log.d(TAG, "东家摸牌后公共牌池剩余：" + publicPool);
        } else {
            Log.d(TAG, "不可摸牌");
            shouToast("不可摸牌");
        }

    }

    /**
     * @param dongHand
     */
    private void judgeIsWin(final List<String> dongHand) {

        List<String> nowhand = new ArrayList<>(dongHand);

        nowhand = getOrder(nowhand);

        Log.d(TAG, "当前手牌: " + nowhand);

        if (nowhand.size() == 14) {

            List<String> sevenList = new ArrayList<>(nowhand);

            for (int i = 1; i < sevenList.size(); i++) {
                if (sevenList.get(i-1).equals(sevenList.get(i))){
                    sevenList.remove(i);
                    sevenList.remove(i-1);
                    i--;
                }
            }
            if (sevenList.size()==0){
                shouToast("胡了，七对子");
                return;
            }


            for (int i = 1; i < nowhand.size(); i++) {
                if (nowhand.get(i - 1).equals(nowhand.get(i))) {
                    //找到一个雀头

                    Log.d(TAG, "雀头：" + nowhand.get(i - 1) + " " + nowhand.get(i));

                    List<String> tempList = new ArrayList<>(nowhand);

                    tempList.remove(i);
                    tempList.remove(i - 1);


                    Log.d(TAG, "雀头带的数组：" + tempList);

                    for (int j = 0; j < tempList.size(); j++) {
                        Log.d(TAG, tempList.get(j) + " 的数量：" + Collections.frequency(tempList, tempList.get(j)));
                        if (Collections.frequency(tempList, tempList.get(j)) == 3) {
//                            某张牌个数为 3 证明是一组刻子

                            //这个刻子删了
                            for (int k = 0; k < tempList.size(); k++) {
                                if (tempList.get(k).equals(tempList.get(j))) {
                                    tempList.remove(tempList.get(j));
                                    k--;
                                }
                            }
                            Log.d(TAG, "删了一组刻子，剩余：" + tempList);
                            j = j - 3;


                        }
                    }

                    if (tempList.size() == 0) {
                        Log.d(TAG, "judgeIsWin: 胡了，4个刻子");
                        shouToast("胡了");
                        return;
                    } else {
                        Log.d(TAG, "judgeIsWin: 没胡，还剩：" + tempList.size());
                        Log.d(TAG, "judgeIsWin: 没胡，还剩：" + tempList);
                    }

                    for (int j = 0; j < ziOrder.length; j++) {
                        if (tempList.contains(ziOrder[j])) {
                            Log.d(TAG, "judgeIsWin: 胡不了了，还有多余大字");
                        }
                    }

                    for (int j = 0; j < tempList.size(); j++) {

                        int k = j + 1;

                        if (getIndex(tempList.get(k)) - 1 == getIndex(tempList.get(j))) {
                            for (int l = k + 1; l < tempList.size(); l++) {
                                if (getIndex(tempList.get(l)) - 1 == getIndex(tempList.get(k))) {
//                                        找到了一个顺子，当前位置 j, k ,l
                                    Log.d(TAG, "找到了一组顺子: " + tempList.get(j) + " " + tempList.get(k) + " " + tempList.get(l));
                                    tempList.remove(l);
                                    tempList.remove(k);
                                    tempList.remove(j);
                                    Log.d(TAG, "删了这组顺子剩余: " + tempList);
                                    j--;
                                    break;

                                }
                            }
                        } else {
                            Log.d(TAG, "judgeIsWin: 没胡，不成顺子");
                            return;
                        }

                    }


                    if (tempList.size() == 0) {
                        Log.d(TAG, "judgeIsWin: 胡了");
                        shouToast("胡了");
                    } else {
                        Log.d(TAG, "judgeIsWin: 没胡，还剩：" + tempList.size());
                        Log.d(TAG, "judgeIsWin: 没胡，还剩：" + tempList);
                    }


                }
            }

        } else {
            Log.d(TAG, "东家相公");
            shouToast("东家相公");
        }

    }

    private void cleanTable(){

        mahjong = new ArrayList<>();

        mountain = new ArrayList<>();

        publicPool = new ArrayList<>();

        dongHand = new ArrayList<>();
        nanHand = new ArrayList<>();
        xiHand = new ArrayList<>();
        beiHand = new ArrayList<>();

        dongPool = new ArrayList<>();
        nanPool = new ArrayList<>();
        xiPool = new ArrayList<>();
        beiPool = new ArrayList<>();

        dongShow = new ArrayList<>();
        nanShow = new ArrayList<>();
        xiShow = new ArrayList<>();
        beiShow = new ArrayList<>();

        startGame();
    }

    private void randomMahjong() {

        for (int i = 0; i < MAHJONG.length; i++) {
            mahjong.add(MAHJONG[i]);
        }
        Collections.shuffle(mahjong);

        Log.d(TAG, "本场牌谱: " + mahjong);

    }

    private void initPlayerHand() {
        Log.d(TAG, "东家起始手牌: " + mahjong.subList(0, 13));

        dongHand.addAll(mahjong.subList(0, 13));

        mahjong.subList(0, 13).clear();

        Log.d(TAG, "南家起始手牌: " + mahjong.subList(0, 13));

        nanHand.addAll(mahjong.subList(0, 13));

        mahjong.subList(0, 13).clear();

        Log.d(TAG, "西家起始手牌: " + mahjong.subList(0, 13));

        xiHand.addAll(mahjong.subList(0, 13));

        mahjong.subList(0, 13).clear();

        Log.d(TAG, "北家起始手牌: " + mahjong.subList(0, 13));

        beiHand.addAll(mahjong.subList(0, 13));

        mahjong.subList(0, 13).clear();

        Log.d(TAG, "手牌分配后剩余: " + mahjong);
    }

    private void initMountain() {

        Log.d(TAG, "山牌: " + mahjong.subList(mahjong.size() - 12, mahjong.size()));

        mahjong.subList(mahjong.size() - 12, mahjong.size()).clear();

        Log.d(TAG, "山牌分配后剩余: " + mahjong);

    }

    private void initPublicPool() {

        publicPool = mahjong;

        Log.d(TAG, "公共牌池起始牌: " + publicPool);

        tvPublicPool.setText("牌池剩余：" + publicPool.size());

    }

    void shouToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }


}
