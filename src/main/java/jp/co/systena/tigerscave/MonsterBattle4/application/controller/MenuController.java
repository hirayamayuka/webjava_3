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

@Controller // Viewあり。Viewを返却するアノテーション
public class MenuController
{
	// Sessionの定義
	@Autowired
	HttpSession session; // セッション管理
	// DBと接続するやつの定義
	@Autowired
	JdbcTemplate jdbcTemplate;


	//メニューに戻るボタンを押した時のメソッド
	@RequestMapping(value="/MonsterBattle4", params="menu=menu" , method = RequestMethod.POST) // URLとのマッピング
	public ModelAndView show_Menu(ModelAndView mav)
	{
		mav.setViewName("Menu");

		return mav;
	}


	//手持ちポケモン一覧に遷移する

	@RequestMapping(value="/MonsterBattle4", params="item=Party" , method = RequestMethod.POST) // URLとのマッピング
	public ModelAndView show_Party(ModelAndView mav)
	{
		Trainer trainer = (Trainer)session.getAttribute("Trainer");
		int trainer_id = trainer.getTrainer_ID();

		//SELECTを使用してテーブルの情報を取得する
	    List<DefaultPoke> partylist = jdbcTemplate.query
	    		("SELECT B.poke_name, B.poke_num , A.level "
	    			+ "FROM trainer A "
	    			+ "left join m_defaultpokemon B "
	    			+ "on A.poke_num = B.poke_num"
	    			+ " where A.trainer_ID = " + trainer_id
	    			, new BeanPropertyRowMapper<DefaultPoke>(DefaultPoke.class));

	    // htmlでの入力を受け取るクラスをインスタンス化
	    Form form = new Form();
	    mav.addObject("Form",form);

	    mav.addObject("Partylist" , partylist);
		mav.setViewName("Party");
		return mav;
	}

	 // 手持ちポケモン画面でステータスボタンを押したとき

	@RequestMapping(value="/MonsterBattle4", params="show=ステータス" , method = RequestMethod.POST) // URLとのマッピング
	public ModelAndView show_Party_status(ModelAndView mav , @Valid Form form)
	{
		int poke_num = form.getPoke_num();

		System.out.println("poke_num is " + poke_num);

		//SELECTを使用してテーブルの情報を取得する
	    List<DefaultPoke> pokestatus = jdbcTemplate.query
	    		("SELECT * FROM m_defaultpokemon "
	    			+ "where poke_num = " + poke_num + ""
	    			, new BeanPropertyRowMapper<DefaultPoke>(DefaultPoke.class));

	    mav.addObject("Pokestatus" , pokestatus);

		return show_Party(mav);
	}



	 //バトル画面に遷移するメソッド
	 @RequestMapping(value="/MonsterBattle4", params="item=Battle" , method = RequestMethod.POST) // URLとのマッピング
	public ModelAndView show_BattleView(ModelAndView mav)
	{

		//SELECTを使用してテーブルの情報を取得する
	    List<DefaultPoke> partylist = jdbcTemplate.query
	    		("SELECT B.poke_name, B.poke_num, hp, attack, speed,poke_typename1,poke_typename2 "
	    			+ "FROM trainer A "
	    			+ "left join m_defaultpokemon B "
	    			+ "on A.poke_num = B.poke_num"
	    			+ " where A.trainer_ID = 1"
	    			, new BeanPropertyRowMapper<DefaultPoke>(DefaultPoke.class));
	    mav.addObject("Partylist" , partylist);

	    // selectで一番最初にヒットしたポケモンの情報をmodelにセット
	    mav.addObject("SelectType", partylist.get(0));

	    mav.addObject("SelectPoke" , 0);

	    // htmlでの入力を受け取るクラスをインスタンス化
	    Form form = new Form();
	    mav.addObject("Form",form);

		mav.setViewName("BattleView");
		return mav;
	}



	// バトル画面でステータスを見るを押されたとき
	@RequestMapping(value="/MonsterBattle4", params="battle_type=battle_status" , method = RequestMethod.POST) // URLとのマッピング
	public ModelAndView show_Battle_status(ModelAndView mav, @Valid Form form)
	{
		int selectpokenum = form.getPoke_num();


		//SELECTを使用してテーブルの情報を取得する
		// プルダウン表示用
	    List<DefaultPoke> partylist = jdbcTemplate.query
	    		("SELECT B.poke_name, B.poke_num, hp, attack, speed "
	    			+ "FROM trainer A "
	    			+ "left join m_defaultpokemon B "
	    			+ "on A.poke_num = B.poke_num"
	    			+ " where A.trainer_ID = 1"
	    			, new BeanPropertyRowMapper<DefaultPoke>(DefaultPoke.class));
	    mav.addObject("Partylist" , partylist);


	    // 選択されたポケモンのスタータスを取得
	    List<DefaultPoke> selectpoke = jdbcTemplate.query
	    		("SELECT * FROM m_defaultpokemon where poke_num = " + form.getPoke_num() + ""
	    			, new BeanPropertyRowMapper<DefaultPoke>(DefaultPoke.class));

	    // selectで一番最初にヒットしたポケモンの情報をmodelにセット
	    mav.addObject("SelectType", selectpoke.get(0));
	    mav.addObject("SelectPoke" , selectpokenum);

	    // htmlでの入力を受け取るクラスをインスタンス化
	    Form new_form = new Form();
	    mav.addObject("Form",new_form);

		mav.setViewName("BattleView");
		return mav;
	}
}
