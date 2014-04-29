package com.example.autoscore;

import java.util.HashMap;

import android.R.integer;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {

	private static SoundManager s_manager;
	private SoundPool soundpool; //���� ó���� �ϴ� ��ü
	
	//���尡 ����Ǵ� ��
	private HashMap<Integer, Integer>map;
	
	private AudioManager a_manager;
	private Context context;
	
	//�ܺο��� ���� Ŭ������ ������ ������ ���ϵ��� (�̱���) �ϱ����� �⺻�����ڸ� ������
	private SoundManager(){}
	//�ʱ�ȭ �޼���
	public void init(Context context){
		this.context = context;
		//1. ���ÿ� ��� ������ ��Ʈ���� , 2. ��Ʈ���� Ÿ��, 3. ���� ǰ�� �⺻�� 0
		soundpool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
		
		a_manager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		
		//���带 ������ ����
		map = new HashMap<Integer, Integer>();
	}
	
	//���� ��ü�� �ϳ��� ������ �� �ֵ��� �ϴ� �޼���
	public static SoundManager getInstance(){
		if(s_manager == null)
			s_manager = new SoundManager();
		return s_manager;
	}
	
	//���带 �߰��ϴ� �޼���
	public void addSound(int idx, int id){
		
		//1. ���带 ������ ��Ƽ��Ƽ(context)
		//2. raw�� ����� id��
		//3. �켱����
		int res_id = soundpool.load(context, id, 0);
		
		//�ε�� ���带 Map���η� ����
		map.put(idx, res_id);
	}
	
	//Ư�� ���带 �����ϴ� �޼���
	public void play(int idx){
		
		//1. soundID : SoundPool.load�� ������ �ĺ���(�����ų ���� id)
		//2. leftVolume : left������
		//3. rightVolume : right������
		//4. priority : �켱����
		//5. loop : �ݺ�Ƚ�� - 0�� 1ȸ, -1�� ���ѹݺ�, 1.2.3.4�� �߰� ���Ƚ��
		//6. rate : ����ӵ� -2�� 2��, 0.5�� ���ݼӵ�
		soundpool.play(map.get(idx), 1, 1, 1, -1, 1);
	}
	
	//�������� ���带 �����ϴ� �޼���
	public void stopSound(int idx){
		soundpool.stop(map.get(idx));
	}
	
}