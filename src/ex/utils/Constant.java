package ex.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;



public class Constant {

	public static final String SDCardRoot = Environment
			.getExternalStorageDirectory().getAbsolutePath() + File.separator;
	public static final String ROOTPATH=SDCardRoot+"launcherpad"+File.separator;
	
	public static final String SERVER_IP = "http://192.168.12.218:9999";
	public static final String SERVER_DATA = SERVER_IP + "/datacenter";
	public static final String PORT = "75c584efdfc64debbef1b89f653b2ea9";
	
	
	/**
	 * 学科书籍地址信息
	 */
	private static String BOOKPATH=ROOTPATH+"book/";
	public static String CHINESEBOOK = BOOKPATH + "chinesebook/";
	public static String MATHBOOK = BOOKPATH + "mathbook/";
	public static String FOREIGNLANGBOOK = BOOKPATH + "foreignlangbook/";
	public static String PHYSICALBOOK = BOOKPATH + "physicalbook/";
	public static String HISTORYBOOK=BOOKPATH + "historybook/";
	public static String GEOGRAPHYBOOK=BOOKPATH + "geographybook/";
	public static String CHEMBOOK=BOOKPATH + "chembook/";
	public static String BIOLOGYBOOK=BOOKPATH + "biologybook/";
	
	/** 签到图像储存位置*/
	public static String signinfilePath=ROOTPATH+"Camera/";
	
	
	public static Map<String, Bitmap> Videomap = new HashMap<String, Bitmap>();
	public static Map<String, Bitmap> ImageMap = new HashMap<String, Bitmap>();
	/** 校情展示 */
	public static final int SCHOOLE_SHOW = 0;
	/** 知识长廊 */
	public static final int SCHOOLE_CULTURE = 1;
	/** 益智游戏 */
	public static final int SCHOOLE_GAMES = 2;
	/** 学科中心 */
	public static final int SCHOOLE_CENTER = 3;
	/** 应用中心*/
	public static final int SCHOOLE_APP=4;

	/** 画廊的图片集合 */
	public static String galleryfilePath = SDCardRoot + "galleryinfo/";
	/** 宣传册 */
	public static String xunchance = SDCardRoot+"xuanchuance/";
	/** 产品 */
	public static String xunfilePath = SDCardRoot + "publish/";
	/** 交大附中 照片路径 */
	public static final String BJTU_MIDDLE_SCHOOL_PATH = SDCardRoot
			+ "jiaodafu/";
	
	/**  作品 */
	public static String zuopin = SDCardRoot + "zuopin/";
	/**  网站 */
	public static String wangzhan = SDCardRoot + "wangzhan/";
	
	
	/** 交大附中 */
	public static final String BJTU_MIDDLE_SCHOOL = "交大附中";

	/** 视频长度 */
	public static final int VIDEO_SIZE = 500;
                                                                                                                                   
	

}
