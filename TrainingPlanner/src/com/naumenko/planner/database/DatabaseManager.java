package com.naumenko.planner.database;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.naumenko.planner.R;
import com.naumenko.planner.components.Drill;

public class DatabaseManager{
	private static final DatabaseManager singlenton = new DatabaseManager();

	private SQLiteStatement insertStatement,
			updateMyTimePixCountStatament, 
			deleteMyTimePixStatament,
			decreaseMyTimePixCountStatament;
	
	private DatabaseHelper helper;
	private Context applicationContext;
	private static final String DRILLS_TABLE = "drills";
	private static final String TAG = "DatabaseManager";
	
	private final String INSERT_SQL_QUERY = "INSERT INTO "
			+ DRILLS_TABLE
			+ "(id, name, description, picture, time, execution, effect,thumbnail) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?,?)";
	
	private final String UPDATE_TIME = "UPDATE " + DRILLS_TABLE	+ " SET time=? where id = ?";

	
	private DatabaseManager() {
	}

	public static DatabaseManager getInstance() {
		return singlenton;
	}

	/**
	 * Database initialization.
	 * 
	 * @param context to use to open or create the database.
	 * 
	 */
	public void init(final Context context) {
		applicationContext = context;
		helper = new DatabaseHelper(context);
		SQLiteDatabase database = helper.getWritableDatabase();
		insertStatement = database.compileStatement(INSERT_SQL_QUERY);
		updateMyTimePixCountStatament = database.compileStatement(UPDATE_TIME);
		
		Log.v("DATABASE", "INIT_OK");
	}
	
	public void beginTransaction(){
		SQLiteDatabase database = helper.getReadableDatabase();
		database.beginTransaction();
	}
	public void endTransaction(){
		SQLiteDatabase database = helper.getReadableDatabase();
		database.setTransactionSuccessful();
		database.endTransaction();
	}

	public boolean isEmpty() {
		return helper.isDatabaseEmpty();
	}

	public void save(Drill bean) {
		Log.e(TAG, "Insert = "+bean);
		
		insertStatement.bindNull(1);
		insertStatement.bindString(2, bean.getName());
		insertStatement.bindString(3, bean.getDescription());
		insertStatement.bindString(4, bean.getPicture());
		insertStatement.bindString(5, bean.getTime());
		insertStatement.bindString(6, bean.getExecution());
		insertStatement.bindString(7, bean.getEffect());
		insertStatement.bindString(8, bean.getThumbnail());
		
		insertStatement.executeInsert();
		insertStatement.clearBindings();
	}

	public ArrayList<Drill> getDrillList() {
		SQLiteDatabase database = helper.getReadableDatabase();

		Cursor cursor = database.query(DRILLS_TABLE, new String[] {
				"name", "description", "picture", "time","execution", "effect","thumbnail","id"}, // columns
				null, // selection
				null, // selectionArgs
				null, // groupBy,
				null, // having,
				"id"); // orderBy;

		ArrayList<Drill> list = new ArrayList<Drill>();
		cursor.moveToNext();

		do {
			Drill bean = new Drill(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),cursor.getInt(7));
			
			list.add(bean);
		} while (cursor.moveToNext());
		cursor.close();
		Log.e("ent", ""+list);
		return list;
	}

	/*public List<ImageListBean> getMyTimePixImageListBean() {
		SQLiteDatabase database = helper.getReadableDatabase();

		Cursor picturesCursor = database.query(PICTURES_TABLE, new String[] {
				"place_id", "count(*) as \"count\"" }, // columns
				null, // selection
				null, // selectionArgs
				"place_id", // groupBy,
				null, // having,
				"place_id"); // orderBy;

		Cursor placesCursor = database.query(DRILLS_TABLE, new String[] {
				"short_title", "position", "time_pix_count" }, // columns
				"time_pix_count>0", // selection
				null, // selectionArgs
				null, // groupBy,
				null, // having,
				"position"); // orderBy;

		LinkedList<ImageListBean> list = new LinkedList<ImageListBean>();

		if (picturesCursor.moveToFirst() && placesCursor.moveToFirst()) {
			String bitmapsPath = applicationContext
					.getDir(applicationContext
							.getString(R.string.my_tyme_pix_thumbnail_root),
							Context.MODE_WORLD_READABLE).getAbsolutePath()
					+ "/";
			do {

				ImageListBean bean = new ImageListBean();

				bean.setSmallImgName(bitmapsPath + picturesCursor.getString(0) + ".jpg");
				bean.setPosition(picturesCursor.getInt(0));
				bean.setAmountOfPhotos(picturesCursor.getInt(1));
				bean.setShortTtitle(placesCursor.getString(0));
				bean.setBitmapIsPath(true);

				list.add(bean);
			} while (picturesCursor.moveToNext() && placesCursor.moveToNext());
		}

		picturesCursor.close();
		placesCursor.close();
		return list;
	}

	public List<OverlayItem> getOverlays() {
		List<OverlayItem> list = new LinkedList<OverlayItem>();

		SQLiteDatabase database = helper.getReadableDatabase();

		Cursor placesCursor = database.query(DRILLS_TABLE, new String[] {
				"latitude", "longitude" }, // columns
				null, // selection
				null, // selectionArgs
				null, // groupBy,
				null, // having,
				"position"); // orderBy;

		placesCursor.moveToNext();

		do {
			int latitude = placesCursor.getInt(0);
			int longitude = placesCursor.getInt(1);

			OverlayItem overlayItem = new OverlayItem(new GeoPoint(latitude,
					longitude), "", "");

			list.add(overlayItem);

		} while (placesCursor.moveToNext());
		placesCursor.close();
		return list;
	}

	*//**
	 * 
	 * @param position
	 *            position of place.
	 * @return TimeShutterBean with filled fields
	 *         "short_title, large_image, orientation"
	 *//*
	public TimeShutterBean getShortBeanByPosition(int position) {
		SQLiteDatabase database = helper.getReadableDatabase();
		TimeShutterBean bean = new TimeShutterBean();

		Cursor cursor = database.query(DRILLS_TABLE, new String[] {
				"large_image", "short_title", "orientation" }, // columns
				"position=" + position, // selection
				null, // selectionArgs
				null, // groupBy,
				null, // having,
				null); // orderBy;
		cursor.moveToFirst();

		bean.setLageImgPath(cursor.getString(0));
		bean.setShort_title(cursor.getString(1));
		bean.setOrientation(cursor.getString(2));
		cursor.close();
		return bean;
	}

	public List<PlaceDescriptionBean> getDescriptionList() {

		LinkedList<PlaceDescriptionBean> listOfBean = new LinkedList<PlaceDescriptionBean>();

		SQLiteDatabase database = helper.getReadableDatabase();

		Cursor cursor = database.query(DRILLS_TABLE, new String[] {
				"large_image", "orientation", "position" }, // columns
				null, // selection
				null, // selectionArgs
				null, // groupBy,
				null, // having,
				"position"); // orderBy;

		if (cursor.moveToNext()) {

			do {
				PlaceDescriptionBean bean = new PlaceDescriptionBean();

				String bitmapName = cursor.getString(0);

				bitmapName = bitmapName.substring(0,
						bitmapName.lastIndexOf(".")).trim();

				int bitmapID = applicationContext.getResources()
						.getIdentifier(
								applicationContext.getPackageName() + Constants.TYPE_DRAWABLE
										+ bitmapName, null, null);

				bean.setResourceId(bitmapID);

				bean.setOrientation(cursor.getString(1));
				bean.setPosition(cursor.getShort(2));
				listOfBean.add(bean);

			} while (cursor.moveToNext());
		}
		return listOfBean;
	}
*/
	public void addMyTimePix(int photoPosition,String value) {
		SQLiteDatabase database = helper.getWritableDatabase();

		updateMyTimePixCountStatament.bindString(1, value);
		updateMyTimePixCountStatament.bindLong(2, photoPosition);
		
		
		database.beginTransaction();
		updateMyTimePixCountStatament.execute();
		database.setTransactionSuccessful();
		database.endTransaction();
		updateMyTimePixCountStatament.clearBindings();
		Log.v("DBMANAGER", "UPDATED");
	}
/*
	public int getPlacesCount() {
		SQLiteDatabase database = helper.getWritableDatabase();
		if (placesCount > 0) {
			return placesCount;
		}

		Cursor cursor = database.query(DRILLS_TABLE,
				new String[] { "count(*)" }, // columns
				null, // selection
				null, // selectionArgs
				null, // groupBy,
				null, // having,
				null); // orderBy;
		cursor.moveToFirst();

		placesCount = cursor.getInt(0);
		cursor.close();
		Log.v("COMPILE", "OK");

		return placesCount;
	}

	public void destroy() {
		helper.getWritableDatabase().close();
	}

	public Map<Long, String> getReshotedImages(int position) {
		LinkedHashMap<Long, String> pathes = new LinkedHashMap<Long, String>();
		SQLiteDatabase database = helper.getReadableDatabase();

		Cursor cursor = database.query(PICTURES_TABLE, new String[] { "path",
				"picture_id" }, // columns
				" place_id = " + position, // selection
				null, // selectionArgs
				null, // groupBy,
				null, // having,
				null); // orderBy;

		if (cursor.moveToNext()) {

			do {
				pathes.put(cursor.getLong(1), cursor.getString(0));
				Log.v("DBMANAGER", "" + pathes);
			} while (cursor.moveToNext());

		}
		return pathes;
	}

	public InfromationBean getInformation(int position) {

		InfromationBean mInfromationBean = null;
		SQLiteDatabase database = helper.getReadableDatabase();

		Cursor cursor = database.query(DRILLS_TABLE, new String[] { "title",
				"street", "year", "body", "postal_code" }, // columns
				" position = " + position, // selection
				null, // selectionArgs
				null, // groupBy,
				null, // having,
				null); // orderBy;

		if (cursor.moveToNext()) {

			do {
				mInfromationBean = new InfromationBean();
				mInfromationBean.setTitle(cursor.getString(0));
				mInfromationBean.setStreet(cursor.getString(1));
				mInfromationBean.setYear(cursor.getString(2));
				mInfromationBean.setBody(cursor.getString(3));
				mInfromationBean.setPostalCode(cursor.getString(4));

			} while (cursor.moveToNext());

		}

		return mInfromationBean;
	}*/

	public void decreasePhotoCount(int picturePosition) {
		SQLiteDatabase database = helper.getWritableDatabase();
		decreaseMyTimePixCountStatament.bindLong(1, picturePosition);
		database.beginTransaction();
		decreaseMyTimePixCountStatament.execute();
		database.setTransactionSuccessful();
		database.endTransaction();
		decreaseMyTimePixCountStatament.clearBindings();
	}

	public void deleteMyTimePix(Long id) {
		SQLiteDatabase database = helper.getWritableDatabase();

		deleteMyTimePixStatament.bindLong(1, id);

		database.beginTransaction();
		deleteMyTimePixStatament.execute();
		database.setTransactionSuccessful();
		database.endTransaction();
		deleteMyTimePixStatament.clearBindings();
	}
	public List<Integer> getAmauntOfPhoto(){
		
		SQLiteDatabase database = helper.getReadableDatabase();

		Cursor cursor = database.query(DRILLS_TABLE, new String[] {
				"time_pix_count", }, // columns
				null, // selection
				null, // selectionArgs
				null, // groupBy,
				null, // having,
				"position"); // orderBy;

		LinkedList<Integer> list = new LinkedList<Integer>();
		cursor.moveToNext();

		do {
			list.add(cursor.getInt(0));
		} while (cursor.moveToNext());
		cursor.close();
		return list;
	}

	
	public Set<Integer> getReshutedPlaces(){
		Set<Integer> set = new HashSet<Integer>();
		SQLiteDatabase database = helper.getReadableDatabase();
		
		Cursor cursor = database.query(DRILLS_TABLE, 
				new String[] {"position"}, // columns
				"time_pix_count>0", // selection
				null, // selectionArgs
				null, // groupBy,
				null, // having,
				null); // orderBy;
		
		if(cursor.moveToFirst()){
			do {
				Integer i = cursor.getInt(0);
				set.add(i);
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return set;
	}
	
	/*public TimeShutterBean getOldImageShortBeanByNewImageId(long ID) {
		SQLiteDatabase database = helper.getReadableDatabase();
		
		Cursor cursor = database.query(PICTURES_TABLE, 
				new String[] {"place_id"}, // columns
				"picture_id=" + ID, // selection
				null, // selectionArgs
				null, // groupBy,
				null, // having,
				null); // orderBy;
		
		long placeID = -1;
		
		if(cursor.moveToFirst()){
			placeID = cursor.getLong(0);
		}
		cursor.close();
		
		return getShortBeanByPosition((int) placeID);
	}*/
}
