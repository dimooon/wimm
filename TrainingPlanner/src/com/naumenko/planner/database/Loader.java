package com.naumenko.planner.database;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.util.Log;

import com.naumenko.planner.R;
import com.naumenko.planner.components.Drill;

public class Loader extends Thread {

	private static final String TAG = "Loader";
	private LoaderObserver observer;
	private DatabaseManager daoImpl;
	private Context context;
	private int i = 0;

	public Loader( Context c) {
		this.daoImpl = DatabaseManager.getInstance();
		this.context = c;
	}

	@Override
	public void run() {
		observer.notifyStart();
		XmlPullParser xpp = null;
		InputStream is = null;
		try {

			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

			factory.setNamespaceAware(true);

			is = context.getResources().openRawResource(R.raw.drills);

			xpp = factory.newPullParser();
			
			xpp.setInput(is, null);

			int eventType = xpp.getEventType();

			Drill bean = new Drill();

			String tName = "";

			final String END_XML_NODE = "drill";

			while (XmlPullParser.END_DOCUMENT != eventType) {
				if (XmlPullParser.START_TAG == eventType) {
					tName = xpp.getName();
				}

				Method m = null;
				if ((tName.length() > 0) && (XmlPullParser.TEXT == eventType) && (!xpp.isWhitespace())) {
					try {
						m = bean.getClass().getMethod(
								"set" + tName.substring(0, 1).toUpperCase()	+ tName.substring(1), String.class);
					} catch (NoSuchMethodException e) {
						Log.e(TAG, "Unknown tag " + tName);
						eventType = xpp.next();
						continue;
					}
					
					if (m == null) {
						observer.notifyUnknownMetod(tName);
						eventType = xpp.next();
						continue;
					}

					m.invoke(bean, xpp.getText());

				}
				if ((XmlPullParser.END_TAG == eventType) && (END_XML_NODE.equalsIgnoreCase(xpp.getName()))) {
					{
						
						Log.v("bean", bean.toString());
						
						daoImpl.save(bean);
					}
				}
				eventType = xpp.next();
			}
		} catch (Exception e) {
			observer.notifyError(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}
		observer.notifyFinish();
	}

	public void setObserver(LoaderObserver lo) {
		observer = lo;
	}
}