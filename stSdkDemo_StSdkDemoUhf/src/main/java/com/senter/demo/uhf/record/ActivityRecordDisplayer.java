package com.senter.demo.uhf.record;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.senter.demo.uhf.common.RecordsBoard;
import com.senter.demo.uhf.record.RecordRWer.XmlOper;
import com.senter.demo.uhf2.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityRecordDisplayer extends Activity
{
	RecordsBoard lvWithOverlap;
	public static final String KeyOnBundleExtra4FileName = "Name";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.e2record_displayer_activity);
		((Button) findViewById(R.id.idRecordDisplayerActivity_btnback)).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});

		lvWithOverlap = new RecordsBoard(this, findViewById(R.id.idE2RecordDisplayerActivity_inShow));

		String string = getIntent().getExtras().getString(KeyOnBundleExtra4FileName);
		ArrayList<HashMap<String, String>> rs = RecordRWer.XmlOper.parseFile(string);

		for (int i = 0; i < rs.size(); i++)
		{
			lvWithOverlap.addMassage(rs.get(i).get(XmlOper.mapKey2Rfid));
		}
	}

}
