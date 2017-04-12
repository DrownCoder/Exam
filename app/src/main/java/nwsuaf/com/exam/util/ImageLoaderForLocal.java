package nwsuaf.com.exam.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.reflect.Field;


public class ImageLoaderForLocal {
	
	private static ImageLoaderForLocal mInstance;
	public static ImageLoaderForLocal getInstance() {
		// 懒加载
		if (mInstance == null) {
			// 同步资源锁，防止不同线程多次调用，占用内存，提高效率
			synchronized (ImageLoaderForLocal.class) {

				if (mInstance == null) {
					mInstance = new ImageLoaderForLocal();
				}
			}
		}
		return mInstance;
	}
	public void loadDrawable(int drawable,ImageView view){
		Options opt = new Options();

		opt.inPreferredConfig = Bitmap.Config.RGB_565;

		opt.inPurgeable = true;

		opt.inInputShareable = true;

		InputStream is = view.getResources().openRawResource(

				 drawable );

		Bitmap bm = BitmapFactory.decodeStream(is, null, opt);
		/*ImageSize imageSize = getImageViewSize(view);
		Bitmap bm = decodeSampledBitmapFromPath(view.getResources(),drawable, imageSize.width, imageSize.height);*/
		view.setImageBitmap(bm);
	}
	/**
	 * 根据图片需要显示的宽和高进行压缩
	 */
	protected Bitmap decodeSampledBitmapFromPath(Resources res,int id, int width,
			int height) {
		//获取图片的宽和高并不把图片加载到内存中
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, id, options);
	//	BitmapFactory.decodeFile(path,options);//options中保存图片的真实宽高
		
		options.inSampleSize = caculateInSampleSize(options,width,height);//处理得到压缩比例
		
		//使用获取到的InSampleSize再次解析图片
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeResource(res, id, options);
		//Bitmap bitmap = BitmapFactory.decodeFile(path,options);
		return bitmap;
	}
/**
 * 根据需求的宽和高以及图片实际的宽和高计算SampleSize
 */
	private int caculateInSampleSize(Options options, int reqwidth, int reqheight) {
		int width = options.outWidth;
		int height = options.outHeight;
		int inSampleSize = 1;
		if(width >reqwidth || height > reqheight){
			int widthRadio = Math.round(width * 1.0f / reqwidth);
			int heightRadio = Math.round(height * 1.0f / reqheight);
			
			inSampleSize = Math.max(widthRadio, heightRadio);
		}
		
		return inSampleSize;
	}

	/**
	 * 根据imageView获取适当的压缩的宽和高
	 */
	protected ImageSize getImageViewSize(ImageView imageView) {
		 ImageSize imageSize = new ImageSize();
		 DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();//将当前窗口的一些信息放在DisplayMetrics类中
		 
		 LayoutParams lp = imageView.getLayoutParams();
		 int width = imageView.getWidth();//获取imageView的实际宽度
		 if(width <= 0){
			 width = lp.width;//获取imageview在layout中声明的宽度
		 }
		 if(width <= 0){
			 //width = imageView.getMaxWidth();//检查最大值
			 width = getImageViewFieldValue(imageView,"mMaxWidth");
		 }
		 if(width <= 0){
			 width = displayMetrics.widthPixels;//屏幕宽度
		 }
		 
		 
		 int height = imageView.getHeight();//获取imageView的实际宽度
		 if(height <= 0){
			 height = lp.height;//获取imageview在layout中声明的宽度
		 }
		 if(height <= 0){
			// height = imageView.getMaxHeight();//检查最大值
			 height = getImageViewFieldValue(imageView,"mMaxHeight");
		 }
		 if(height <= 0){
			 height = displayMetrics.heightPixels;
		 }
		 
		 imageSize.width = width;
		 imageSize.height = height;
		 
		return imageSize;
	}
	/**
	 * 通过反射获取ImageView的某个属性值
	 */
	private static int getImageViewFieldValue(Object object,String fieldName){
		int value = 0;
		try {
		Field field = ImageView.class.getDeclaredField(fieldName);
		field.setAccessible(true);
		
		int fieldValue = field.getInt(object);
		if(fieldValue>0 && fieldValue< Integer.MAX_VALUE){
			value = fieldValue;
		}
		} catch (Exception e) {
		}
		return value;
	}
	private class ImageSize {
		int width;
		int height;
	}
}
