package nxt.libs.addon;

import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorHTSensor;
import lejos.robotics.Color;

/***
 * Hitech社Colorセンサーの測定を続けるスレッドのクラス
 * Singletonパターンを使うので，一つしかインスタンスを作成できない
 * 使用法：
 * <ol>
 * <li> getInstance()でインスタンスを取得する
 * <li> 1.で取得したインスタンスに対し，start()を実行し，測定開始
 * <li> 測定値を得たいときは，getColor(), getRed(), getGreen(), getBlue(), getColorID(), getColorName()を実行
 * <li> スレッドの実行を終了するには，stopThread()を実行
 * </ol>
 * @author mmotoki
 *
 */
public class SensorChecker extends Thread {
    /**
     * 使用するHitech社製カラーセンサー
     */
    private static ColorHTSensor _sensor = new ColorHTSensor(SensorPort.S3);
    /**
     * Singletonパターンとするための，唯一のインスタンス
     */
    private static SensorChecker _sensorChecker = new SensorChecker();
    /**
     * 色番号と色の名前の対応テーブル
     */
    private final String[] colorName = {"red", "green", "blue", "yellow", "magenta", "orange", "white", "black", "pink", "gray", "light gray", "dark gray", "cyan"};
    /**
     * 測定値を保存する
     */
    private Color _color;
    /**
     * スレッドがアクティブならtrue
     */
    private boolean _isActive = true;
    /**
     * センサーチェック間隔 (ms)
     * setIntervalメソッドでも設定可能
     */
    private int _interval = 50;

    /**
     * Singleton パターンとするために，コンストラクタはprivateとなっている
     */
    private SensorChecker() {
    }

    /**
     * インスタンスの取得
     *
     * @return 唯一のインスタンス
     */
    public static SensorChecker getInstance() {
        return _sensorChecker;
    }

    /**
     * カラーセンサー計測スレッドの実行停止
     */
    public void stopThread() {
        this._isActive = false;
    }

    /**
     * カラーセンサー計測スレッドの開始
     */
    public void run() {
        _isActive = true;
        while (_isActive) {
            _color = _sensor.getColor();
            try {
                Thread.sleep(_interval);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * センサーチェック間隔時間の設定
     *
     * @param interval センサーチェック間隔時間(ms)
     */
    public void setInterval(int interval) {
        _interval = interval;
    }

    /**
     * センサーで取得した色情報の取得
     *
     * @return センサーで取得した色情報
     */
    public Color getColor() {
        return _color;
    }

    /**
     * センサーで取得した色情報のRed成分の取得
     *
     * @return センサーで取得したRed成分 0～255
     */
    public int getRed() {
        return _color.getRed();
    }

    /**
     * センサーで取得した色情報のGreen成分の取得
     *
     * @return センサーで取得したGreen成分 0～255
     */
    public int getGreen() {
        return _color.getGreen();
    }

    /**
     * センサーで取得した色情報のBlue成分の取得
     *
     * @return センサーで取得したBlue成分 0～255
     */
    public int getBlue() {
        return _color.getBlue();
    }

    /**
     * センサーで識別した色番号の取得
     *
     * @return センサーで識別した色番号
     */
    public int getColorID() {
        return _color.getColor();
    }

    /**
     * センサーで識別した色の名前の取得
     *
     * @return センサーで識別した色の名前
     */
    public String getColorName() {
        return colorName[getColorID()];
    }

}
