package jp.co.systena.tigerscave.MonsterBattle4.application.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.systena.tigerscave.MonsterBattle4.application.model.Trainer;

@Controller // Viewあり。Viewを返却するアノテーション
public class SaveController
{
	// Sessionの定義
	@Autowired
	HttpSession session; // セッション管理

	// DBと接続するやつの定義
	@Autowired
	JdbcTemplate jdbcTemplate;

	 @RequestMapping(value="/MonsterBattle4", params="item=Save" , method = RequestMethod.POST) // URLとのマッピング
	public ModelAndView save(ModelAndView mav)
	{


		Trainer trainer = (Trainer)session.getAttribute("Trainer");
		int trainer_ID = trainer.getTrainer_ID();
		String trainer_name = trainer.getTrainer_name();
		String message;

		if(trainer_ID == 0)
		{
			int deleteCount = jdbcTemplate.update("DELETE FROM trainer WHERE trainer_id = 1");

			if(deleteCount == 0)
			{
				message = "前のデータはありませんでした。";
				mav.addObject("message", message);
			}else {
				;
			}

			int updateCount = jdbcTemplate.update("UPDATE trainer SET trainer_id = 1");

			trainer.setTrainer_ID(1);
			session.setAttribute("Trainer", trainer);


			message = "セーブしました。";
			mav.addObject("message", message);

		}
		else
		{
			message = "セーブしました。";
			mav.addObject("message", message);
		}
	mav.setViewName("Menu");
	return mav;

	}
}
