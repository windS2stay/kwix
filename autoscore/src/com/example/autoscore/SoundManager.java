package com.example.autoscore;

import java.util.HashMap;

import android.R.integer;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {

	private static SoundManager s_manager;
	private SoundPool soundpool; //사운드 처리를 하는 객체
	
	//사운드가 저장되는 곳
	private HashMap<Integer, Integer>map;
	
	private AudioManager a_manager;
	private Context context;
	
	//외부에서 현재 클래스를 여러개 만들지 못하도록 (싱글턴) 하기위해 기본생성자를 재정의
	private SoundManager(){}
	//초기화 메서드
	public void init(Context context){
		this.context = context;
		//1. 동시에 출력 가능한 스트림수 , 2. 스트림의 타입, 3. 사운드 품질 기본값 0
		soundpool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
		
		a_manager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		
		//사운드를 저장할 공간
		map = new HashMap<Integer, Integer>();
	}
	
	//현재 객체가 하나만 생성될 수 있도록 하는 메서드
	public static SoundManager getInstance(){
		if(s_manager == null)
			s_manager = new SoundManager();
		return s_manager;
	}
	
	//사운드를 추가하는 메서드
	public void addSound(int idx, int id){
		
		//1. 사운드를 실행한 액티비티(context)
		//2. raw에 등록한 id값
		//3. 우선순위
		int res_id = soundpool.load(context, id, 0);
		
		//로드된 사운드를 Map구로로 저장
		map.put(idx, res_id);
	}
	
	//특정 사운드를 시작하는 메서드
	public void play(int idx){
		
		//1. soundID : SoundPool.load가 리턴한 식별자(재생시킬 사운드 id)
		//2. leftVolume : left볼륨값
		//3. rightVolume : right볼륨값
		//4. priority : 우선순위
		//5. loop : 반복횟수 - 0은 1회, -1은 무한반복, 1.2.3.4는 추가 재생횟수
		//6. rate : 재생속도 -2는 2배, 0.5는 절반속도
		soundpool.play(map.get(idx), 1, 1, 1, -1, 1);
	}
	
	//실행중인 사운드를 정지하는 메서드
	public void stopSound(int idx){
		soundpool.stop(map.get(idx));
	}
	
}