package jp.co.systena.tigerscave.MonsterBattle4.application.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.systena.tigerscave.MonsterBattle4.application.model.DefaultPoke;
import jp.co.systena.tigerscave.MonsterBattle4.application.model.Form;
import jp.co.systena.tigerscave.MonsterBattle4.application.model.Trainer;
import jp.co.systena.tigerscave.MonsterBattle4.application.model.Type;

@Controller // Viewあり。Viewを返却するアノテーション
public class StartController
{
	// Sessionの定義
	@Autowired
	HttpSession session; // セッション管理

	// DBと接続するやつの定義
	@Autowired
	JdbcTemplate jdbcTemplate;

	@RequestMapping(value="/MonsterBattle4", method = RequestMethod.GET) // URLとのマッピング
	public ModelAndView start(ModelAndView mav)
	{
		String table = "m_type";

		//SELECTを使用してテーブルの情報をすべて取得する
	    List<Type> list = jdbcTemplate.query("SELECT * FROM " + table + " ORDER BY type_num", new BeanPropertyRowMapper<Type>(Type.class));

	    mav.addObject("list" , list);
		mav.setViewName("Start");
		return mav;
	}

	@RequestMapping(value="/MonsterBattle4", params="start=New" , method = RequestMethod.POST) // URLとのマッピング
	public ModelAndView show_inputname(ModelAndView mav)
	{
		// htmlでの入力を受け取るクラスをインスタンス化
		Form form = new Form();
		mav.addObject("Form",form);

		mav.setViewName("InputName");
		return mav;
	}

	// つづきから
	@RequestMapping(value="/MonsterBattle4", params="start=Continu" , method = RequestMethod.POST) // URLとのマッピング
	public ModelAndView show_continumenu(ModelAndView mav)
	{
		String table = "trainer";

		//SELECTを使用してテーブルの情報を取得する
	    List<Trainer> continulist = jdbcTemplate.query
	    		("SELECT * "
	    			+ "FROM " + table
	    			+ " where trainer_ID = 1"
	    			, new BeanPropertyRowMapper<Trainer>(Trainer.class));

	    
	    Trainer trainer = new Trainer();
	    int trainer_id = 1;
	    String trainer_name = continulist.get(0).getTrainer_name();
	    trainer.setTrainer_ID(trainer_id);
	    trainer.setTrainer_name(trainer_name);
	    session.setAttribute("Trainer", trainer);
	    session.setAttribute("continu" , continulist);

		mav.setViewName("Menu");
		return mav;
	}


	@RequestMapping(value="/MonsterBattle4", params="button=つぎへ" , method = RequestMethod.POST) // URLとのマッピング
	public ModelAndView show_firstpoke(ModelAndView mav , @Valid Form form)
	{
		// 入力からトレーナー名を取得する
		String trainer_name = form.getTrainer_name();
		int trainer_ID = 0;
		Trainer trainer = new Trainer();
		System.out.println("trainer_name" +  trainer_name );

		trainer.setTrainer_name(trainer_name);
		trainer.setTrainer_ID(trainer_ID);

		// セッションにトレーナーをセットする
		session.setAttribute("Trainer", trainer);

		mav.setViewName("FirstPoke");
		return mav;

	}


	// 最初のポケモンのステータスをみる
	@RequestMapping(value="/MonsterBattle4", params="button=ステータスをみる" , method = RequestMethod.POST) // URLとのマッピング
	public ModelAndView show_status(ModelAndView mav , @Valid Form form)
	{
		String table = "m_defaultpokemon";
		int firstpokenum = form.getNum();

		//SELECTを使用してテーブルの情報を取得する
	    List<DefaultPoke> firstlist = jdbcTemplate.query
	    		("SELECT * "
	    			+ "FROM " + table
	    			+ " where poke_num in (" + firstpokenum + ")"
	    			, new BeanPropertyRowMapper<DefaultPoke>(DefaultPoke.class));

	    mav.addObject("First" , firstlist);
	    session.setAttribute("First" , firstlist);
	    mav.addObject("SelectPoke" , firstpokenum);

	    mav.setViewName("FirstPoke");
		return mav;
	}


	// 最初のポケモンを決定したとき
	@RequestMapping(value="/MonsterBattle4", params="button=けってい" , method = RequestMethod.POST) // URLとのマッピング
	public ModelAndView show_menu(ModelAndView mav , @Valid Form form)
	{
		// 選択したポケモンを取得
		int firstpokenum = form.getNum();
//		String trainer_name = "test";
		Trainer trainer = (Trainer)session.getAttribute("Trainer");
		String trainer_name = trainer.getTrainer_name();
//		String trainer_name = form.getTrainer_name();
		session.setAttribute("First", firstpokenum);

		String table = "trainer";

		//SELECTを使用してテーブルの情報を取得する
/*	    List<Trainer> Trainerlist = jdbcTemplate.query
	    		("Insert into " + table +
	    			" Values " + "(1 , '" + trainer_name + "' , " + firstpokenum + " )"
	    			, new BeanPropertyRowMapper<Trainer>(Trainer.class));
*/
	    int insertCount = jdbcTemplate.update
	    		("INSERT INTO trainer VALUES( ?, ?, ? ,?)",
	    		0,
	    		trainer_name,
	    		firstpokenum,
	    		1);



//	    session.setAttribute("Trainer_info" , Trainerlist);

		// メニュー画面に飛ぶ
		mav.setViewName("Menu");
		return mav;

	}
}
