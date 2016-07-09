

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import com.example.yjy.R;

/**
 * @author Zhang, Tianyou  * @version 2014年12月02日 上午10:51:56
 */
class MainActivity extends Activity {
	long[] mHits = new long[2];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void click(View view) {        //每点击一次 实现左移一格数据
		System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);        //给数组的最后赋当前时钟值
		mHits[mHits.length - 1] = SystemClock.uptimeMillis();        //当0出的值大于当前时间-500时
		// 证明在500秒内点击了2次
		if (mHits[0] > SystemClock.uptimeMillis() - 500) {
			Toast.makeText(this, "被双击了", Toast.LENGTH_SHORT).show();
		}
	}
}