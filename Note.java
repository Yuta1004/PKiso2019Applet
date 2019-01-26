public class Note{
    private int lane;
    private int yBias;
    private float offset;
    private float bpm;
    private boolean isAlive = true;
    private float noteXSpeed;

    // コンストラクタ
    public Note(int lane, float offset, float bpm){
        this.lane = lane;
        this.offset = offset;
        this.bpm = bpm;
        this.noteXSpeed = (float)(200.0) / (float)(6000.0 / bpm);

        if(lane == 0){
            this.yBias = 150;
        }else{
            this.yBias = 400;
        }
    }

    // ノーツの座標を返す(このメソッド内で画像の描画を行うととても面倒なため)
    public Pos getDrawPos(){
        if(offset < -100 || 700 < offset || !isAlive) return new Pos(-1, -1);

        return new Pos((int)offset + 100 - 50, (int)(0.007 * (offset % 200 - 100) * (offset % 200 - 100)) + yBias - 50);
    }

    // ノーツ移動
    public void move(){
        offset -= noteXSpeed;
    }

    // ジャッジ
    public boolean judge(int pressedLane){
        // 判定レーンが違う
       if(pressedLane != lane) return false;

       // +-10フレームでパーフェクト
       if(-10 * noteXSpeed < offset && offset < 10 * noteXSpeed){
           isAlive = false;
           return true;
       }else{
           return false;
       }
    }
}

