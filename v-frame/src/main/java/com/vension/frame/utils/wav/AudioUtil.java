package com.vension.frame.utils.wav;

import android.util.Log;

import com.vension.frame.utils.FileCache;
import com.vension.frame.utils.TimeUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/5/5.
 */

public class AudioUtil {
	static final public String AGENT_PATH_RECORD_AUDIO = FileCache.getCacheAudioPath() + "/";
	/**安卓 合并多段音频
	 * 返回合并后的文件名
	 *
	 * 去掉第二个文件的文件头，改下第一个文件的Chunksize和subchunksize2那两个字段，把字节数加上去
	 * */
	public static String mergeAudio(List<String> audiosToMerge){
		String outName = "";
		WavFileReader mWavFileReader = new WavFileReader();
		WavFileWriter mWavFileWirter = new WavFileWriter();
		try {
            String filePath_0 = AGENT_PATH_RECORD_AUDIO + audiosToMerge.get(0);
			//读取第一个音频文件的头部信息，以便合并后写入新的头部
			mWavFileReader.openFile(filePath_0);
			int sampleRateInHz = mWavFileReader.getmWavFileHeader().mSampleRate;
			int bitsPerSample = mWavFileReader.getmWavFileHeader().mBitsPerSample;
			int channels = mWavFileReader.getmWavFileHeader().mNumChannel;

			//拼接合并后的文件名
			String videoName = audiosToMerge.get(0);
			int n = videoName.lastIndexOf("_");//最后一个=的位置
			String timeStamp = videoName.substring(n + 1,videoName.length() - 4);
			String newFristName = videoName.substring(0,n+1);
			String date = TimeUtil.millis2Date(TimeUtil.string2Millis(timeStamp,"yyyy年MM月dd日HH时mm分ss秒"),"yyyy-MM-dd");
			outName = newFristName + date + ".wav";

			//开始写入合并文件的头部
			mWavFileWirter.openFile(AGENT_PATH_RECORD_AUDIO + outName, sampleRateInHz, bitsPerSample, channels);
			for (int i = 0; i < audiosToMerge.size(); i++) {
				File audioFile = new File(FileCache.getCacheAudioPath(), audiosToMerge.get(i));
				mWavFileReader.openFile(audioFile.getAbsolutePath());
				FileInputStream fis = new FileInputStream(audioFile);
				byte[] temp = new byte[fis.available()];
				int len = temp.length;
				int offset = len - mWavFileReader.getmWavFileHeader().mSubChunk2Size;
				int regionLength = mWavFileReader.getmWavFileHeader().mSubChunk2Size;
				fis.read(temp, offset, regionLength);//读取PCM数据写入temp[] byte数组
				mWavFileWirter.writeData(temp, 0, regionLength);//从temp[]中从0读取regionLength长度的字节写入到目标文件
				fis.close();//释放资源
			}
				mWavFileReader.closeFile();
				mWavFileWirter.closeFile();
				getOutFileHeader(outName);
			//删除掉已合并的文件
			for (String s : audiosToMerge) {
				if (s.equals(outName)){
					audiosToMerge.remove(s);
				}
			}
			deleteAllFiles(audiosToMerge);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return outName;
	}

	private static void getOutFileHeader(String outName) {
		WavFileReader _WavFileReader = new WavFileReader();
		try {
			File _File = new File(FileCache.getCacheAudioPath(),outName);
			_WavFileReader.openFile(_File.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private static void deleteAllFiles(List<String> videosToMerge) {
		File f = new File(FileCache.getCacheAudioPath());
		if(f.exists()){
			for(int i = 0;i < videosToMerge.size();i++){
				File file = new File(FileCache.getCacheAudioPath(), videosToMerge.get(i));
				file.delete(); //删除文件夹（song,art,lyric）
			}
			Log.i("delete_success", "删除成功");
		}else{
			Log.i("delete_fail", "删除失败");
		}
	}

}
