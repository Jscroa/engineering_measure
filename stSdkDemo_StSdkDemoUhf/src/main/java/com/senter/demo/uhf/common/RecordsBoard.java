package com.senter.demo.uhf.common;

import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.senter.demo.uhf2.R;

import java.util.ArrayList;
import java.util.HashMap;

public class RecordsBoard
{
	private final Activity owner;

	private final ListView lv;
	private final ArrayList<HashMap<String, String>> dataSequence = new ArrayList<HashMap<String, String>>(); // data for sequence list
	private final ArrayList<HashMap<String, String>> dataMerged = new ArrayList<HashMap<String, String>>(); // data for merged list

	private final SimpleAdapter adapterSequenceData; // adapter for sequence list
	private final SimpleAdapter adapterMergedData; // adapter for merged list

	private final TextView tvCount;
	public RecordsBoard(Activity context, View ll)
	{
		owner = context;

		adapterSequenceData = new SimpleAdapter(owner, dataSequence, R.layout.common_lv2overlap_tv1, new String[] { "0" }, new int[] { R.id.idE2CommenLv2OverlapTv1_tv0 });
		adapterMergedData = new SimpleAdapter(owner, dataMerged, R.layout.common_lv2overlap_tv2, new String[] { "0", "1" }, new int[] { R.id.idE2CommenLv2OverlapTv1_tv0, R.id.idE2CommenLv2OverlapTv1_tv1 });

		lv = (ListView) ll.findViewById(R.id.idE2CommenLv2Overlap_lvShow);
		lv.setAdapter(adapterSequenceData);

		final CheckBox cbOl = (CheckBox) ll.findViewById(R.id.idE2CommenLv2Overlap_cbOverlap);
		cbOl.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{// set adapter in accordance with user choice
				if (cbOl.isChecked())
				{
					lv.setAdapter(adapterMergedData);
				} else
				{
					lv.setAdapter(adapterSequenceData);
				}
			}
		});
		tvCount=(TextView) ll.findViewById(R.id.idE2CommenLv2Overlap_tvTagsCount);
	}

	public void addMassage(	final String string)
	{
		owner.runOnUiThread(new Runnable()
		{

			@Override
			public void run()
			{
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("0", string);
				map.put("1", "" + 1);

				//update the sequnce data list
				dataSequence.add(map);

				{// update the merged data list
					int index = 0;
					for (; index < dataMerged.size(); index++)
					{
						HashMap<String, String> omap = dataMerged.get(index);
						if (omap.get("0").equals(string))
						{// found it
							int num = 1;

							String numString = omap.get("1");
							if (numString != null)
							{
								try
								{
									num = Integer.valueOf(numString);
								}
								catch (Exception e)
								{
									e.printStackTrace();
								}
							}
							num++;
							omap.put("1", "" + num);
							break;
						}
					}
					if (index == dataMerged.size())
					{// not found in merged data list
						dataMerged.add(map);
					}
				}
				tvCount.setText(""+dataMerged.size());

				adapterSequenceData.notifyDataSetChanged();
				adapterMergedData.notifyDataSetChanged();
			}
		});
	}

	public void clearMsg()
	{// clear data
		owner.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				dataSequence.clear();
				dataMerged.clear();
				tvCount.setText(""+dataMerged.size());
				adapterSequenceData.notifyDataSetChanged();
				adapterMergedData.notifyDataSetChanged();
			}
		});
	}

	public ArrayList<HashMap<String, String>> getData()
	{
		ArrayList<HashMap<String, String>> ret = new ArrayList<HashMap<String, String>>();
		ret.addAll(dataSequence);
		return ret;
	}

	public boolean isEmpty()
	{
		assert(dataSequence.isEmpty()==dataSequence.isEmpty());
		return dataSequence.isEmpty();
	}
}
