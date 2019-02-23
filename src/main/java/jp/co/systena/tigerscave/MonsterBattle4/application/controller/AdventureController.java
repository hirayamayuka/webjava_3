package jp.co.systena.tigerscave.MonsterBattle4.application.controller;

import java.util.List;
import java.util.Random;

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
public class AdventureController
{
	// Sessionの定義
	@Autowired
	HttpSession session; // セッション管理
	// DBと接続するやつの定義
	@Autowired
	JdbcTemplate jdbcTemplate;

	// ぼうけん画面に遷移
	@RequestMapping(value="/MonsterBattle4", params="item=Adventure" , method = RequestMethod.POST) // URLとのマッピング
	public ModelAndView show_Adventure(ModelAndView mav)
	{
		// ランダムで相手のポケモンを作成
		// 乱数を使って相手のpoke_numを決定した後、SQLで対象のポケモンのステータスを取得

		Random rnd = new Random();
		int yaseinum = rnd.nextInt(8)+1;

		System.out.println("yaseinum is " + yaseinum);

		// selectPoke_numを使って、SQLで対象のポケモンのステータスを取得する。
		List<DefaultPoke> yaseilist = jdbcTemplate.query
				("SELECT * "
					+ "FROM m_defaultpokemon "
					+ "where poke_num = " + yaseinum
					, new BeanPropertyRowMapper<DefaultPoke>(DefaultPoke.class));

		mav.addObject("yasei_num", yaseilist.get(0).getPoke_num());


		// 表示メッセージを作成
		String message = "やせいの" + yaseilist.get(0).getPoke_name() + "がとびだしてきた！";
		mav.addObject("message", message);

		// htmlでの入力を受け取るクラスをインスタンス化
	    Form form = new Form();
	    mav.addObject("Form",form);

	    // ゲットボタンの表示
	    String get_mode = "get";
	    mav.addObject("get_mode", get_mode);


		mav.setViewName("Adventure");
		return mav;
	}

	// ゲットボタンが押されたとき
	@RequestMapping(value="/MonsterBattle4", params="get=get1" , method = RequestMethod.POST) // URLとのマッピング
	public ModelAndView get(ModelAndView mav, @Valid Form form)
	{
		Random rnd = new Random();
		int getkakuritu = rnd.nextInt(10);

		if(getkakuritu < 5)
		{
			Trainer trainer = (Trainer)session.getAttribute("Trainer");
			int trainer_ID = trainer.getTrainer_ID();
			String trainer_name = trainer.getTrainer_name();
			int poke_num = form.getPoke_num();
			int update_poke_num = poke_num;

			List<Trainer> getlist = jdbcTemplate.query
					("SELECT * "
						+ "FROM trainer "
						+ "where poke_num = " + poke_num
						+ " and trainer_id = " + trainer_ID
						, new BeanPropertyRowMapper<Trainer>(Trainer.class));

			if(getlist.size() == 0)
			{
				int insertCount = jdbcTemplate.update(
				 "INSERT INTO trainer VALUES( ?, ?, ? ,?)",
				 	trainer_ID,
				 	trainer_name,
				 	poke_num,
					1);
			}
			else
			{
				// 現在のレベルを取得
				int level = getlist.get(0).getLevel();

				// 現在のレベルに+1をする。・・・②
				level = level + 1;

				// m_defaultpokemonテーブルから、poke_numで、データを取得する。・・・①
				List<DefaultPoke> mpokelist = jdbcTemplate.query
						("SELECT * "
							+ "FROM m_defaultpokemon "
							+ "where poke_num = " + poke_num
							, new BeanPropertyRowMapper<DefaultPoke>(DefaultPoke.class));

				// ①から、evolution_level と nextpoke_num を取得
				int evolution_level = mpokelist.get(0).getEvolution_lv();
				int nextpokenum = mpokelist.get(0).getNextpoke_num();

				// evolution_level が ②の数値と等しくなったら、
				// pokenum にnextpoke_num の値を入れる。
				if(evolution_level == level)
				{
					poke_num = nextpokenum;
				}

				// trainerテーブルに更新をかける。
				int updateCount = jdbcTemplate.update(
						"UPDATE trainer SET poke_num = ? ,level = ? "
						+ "WHERE trainer_ID = ? and poke_num = ?",
						poke_num,
						level,
						trainer_ID,
						update_poke_num
						);
			}

			String message = "ゲットだぜ！";
			mav.addObject("message", message);
		}

		else
		{
			String message = "逃げられた。。。";
			mav.addObject("message", message);
		}

		mav.setViewName("Adventure");
		return mav;
	}


}
