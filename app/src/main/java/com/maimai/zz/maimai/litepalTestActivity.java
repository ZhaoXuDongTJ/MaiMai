package com.maimai.zz.maimai;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.maimai.zz.maimai.bombs.StudentInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import me.yuqirong.cardswipelayout.CardConfig;
import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;


public class litepalTestActivity extends AppCompatActivity {
    // 共享 存储
    public SharedPreferences pref;
    private String ObjectId;
    private List<Integer> list = new ArrayList<>();
   // private List<String> list = new ArrayList<String>();
//    private List<Uri> list = new ArrayList<>();
   // private List<Bitmap> list = new ArrayList<Bitmap>();


    public int temp = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litepal_test);
        pref = getSharedPreferences("data",MODE_PRIVATE);
        ObjectId = pref.getString("ObjectID","");
        temp = 0;
        initView();
        initData();



    }

    private void initView() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new MyAdapter());

        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(recyclerView.getAdapter(), list);
        cardCallback.setOnSwipedListener(new OnSwipeListener<Integer>() {

            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {

                MyAdapter.MyViewHolder myHolder = (MyAdapter.MyViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);
                if (direction == CardConfig.SWIPING_LEFT) {
                    myHolder.dislikeImageView.setAlpha(Math.abs(ratio));
                } else if (direction == CardConfig.SWIPING_RIGHT) {
                    myHolder.likeImageView.setAlpha(Math.abs(ratio));
                } else {
                    myHolder.dislikeImageView.setAlpha(0f);
                    myHolder.likeImageView.setAlpha(0f);
                }
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Integer o, int direction) {
                MyAdapter.MyViewHolder myHolder = (MyAdapter.MyViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1f);
                myHolder.dislikeImageView.setAlpha(0f);
                myHolder.likeImageView.setAlpha(0f);
                // 修改 名字  更新
               // Toast.makeText(litepalTestActivity.this, direction == CardConfig.SWIPED_LEFT ? "swiped left" : "swiped right", Toast.LENGTH_SHORT).show();

                ///   购买 此书  修改 数据库
                if(! (direction == CardConfig.SWIPED_LEFT)){


                    StudentInfo studentInfo = new StudentInfo();
                    studentInfo.increment("bookBuy");
                    studentInfo.increment("receiptGood");
                    studentInfo.update(ObjectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(litepalTestActivity.this,"此书已经收入囊中",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onSwipedClear() {
               // Toast.makeText(litepalTestActivity.this, "data clear", Toast.LENGTH_SHORT).show();
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }, 0);
            }

        });
        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        final CardLayoutManager cardLayoutManager = new CardLayoutManager(recyclerView, touchHelper);
        recyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    private void initData() {
        //  传 数据  照片
//        list.add(R.drawable.img_avatar_01);
//        list.add(R.drawable.img_avatar_02);
//        list.add(R.drawable.img_avatar_03);
//        list.add(R.drawable.img_avatar_04);
//        list.add(R.drawable.img_avatar_05);
//        list.add(R.drawable.img_avatar_06);
//        list.add(R.drawable.img_avatar_07);


        switch (temp){

            case 0:
                             list.add(R.drawable.zen0);
                break;
            case 1:        list.add(R.drawable.zen21);
                break;
            case 2:         list.add(R.drawable.zen2);
                break;
            case 3:        list.add(R.drawable.zen3);
                break;
            case 4:        list.add(R.drawable.zen4);
                break;
            case 5:        list.add(R.drawable.zen5);
                break;
            case 6:         list.add(R.drawable.zen6);
                break;
            case 7:        list.add(R.drawable.zen7);
                break;
            case 8:        list.add(R.drawable.zen8);
                break;
            case 9:         list.add(R.drawable.zen9);
                break;
            case 11:        list.add(R.drawable.zen10);
                break;
            case 12:         list.add(R.drawable.zen11);
                break;
            case 10:        list.add(R.drawable.zen13);
                break;
            case 13:        list.add(R.drawable.zen14);
                break;
            case 14:        list.add(R.drawable.zen15);
                break;
            case 15:         list.add(R.drawable.zen16);
                break;
            case 16:         list.add(R.drawable.zen17);
                break;
            case 17:        list.add(R.drawable.zen18);
                break;
            case 18:         list.add(R.drawable.zen19);
                break;
            case 19:        list.add(R.drawable.zen20);
                break;
            case 20:         list.add(R.drawable.zen1);
                break;
            case 21:         list.add(R.drawable.zen22);
                break;
            case 22:  list.add(R.drawable.zen9);break;
         default:
             list.add(R.drawable.zen2);
             break;

        }

        temp ++;
        if(temp == 22){
            temp=0;
        }



//        BmobQuery<BookLibBomb> query = new BmobQuery<BookLibBomb>();
//        query.addWhereEqualTo("StudentID","20162434");
//        query.setLimit(7);
//        query.findObjects(new FindListener<BookLibBomb>() {
//            @Override
//            public void done(final List<BookLibBomb> lists, BmobException e) {
//                if(null ==e){
//                    for(BookLibBomb bookLibBomb : lists){
//                        BmobFile bmobFile = bookLibBomb.getCover();
//                        if(bmobFile!=null){
//                             final String filepath = ImgUtils.FilePath();
//                            final File saveFile = new File(filepath);
//
//                           // list.add(Uri.parse(bmobFile.getFileUrl()));
//
//                            bmobFile.download(saveFile, new DownloadFileListener() {
//                                @Override
//                                public void done(String s, BmobException e) {
//                                    Toast.makeText(litepalTestActivity.this,"下载OK", Toast.LENGTH_SHORT).show();
//
//
//
//
//                                }
//                                @Override
//                                public void onProgress(Integer integer, long l) {
//
//                                }
//                            });
//
////                            list.add(ImgUtils.toUri(filepath));
//                        }else {
//                            Toast.makeText(litepalTestActivity.this,"bmobFile:没有", Toast.LENGTH_SHORT).show();
//
//                      }
////
//                    }
//                }else {
//                    // Error
//                    Toast.makeText(litepalTestActivity.this,"网络超时", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
////






//
//        Toast.makeText(litepalTestActivity.this, ""+R.drawable.img_avatar_01, Toast.LENGTH_SHORT).show();

//        BmobQuery<BookLibBomb> bmobQuery = new BmobQuery<BookLibBomb>();
//        bmobQuery.addWhereEqualTo("cover","images.jpeg");
//        bmobQuery.setLimit(7);
//        bmobQuery.findObjects(new FindListener<BookLibBomb>() {
//            @Override
//            public void done(List<BookLibBomb> lista, BmobException e) {
//                for(BookLibBomb i : lista){
//                    BmobFile bmobFile = new BmobFile();
//                    String filePath = ImgUtils.FilePath();
//                    File saveFile = new File(filePath);
//                    list.add(filePath);
//                    bmobFile.download(saveFile, new DownloadFileListener() {
//                        @Override
//                        public void done(String s, BmobException e) {
//
//                        }
//
//                        @Override
//                        public void onProgress(Integer integer, long l) {
//
//                        }
//                    });
//
//
//                }
//            }
//        });
//





    }

    private class MyAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ImageView avatarImageView = ((MyViewHolder) holder).avatarImageView;
            avatarImageView.setImageResource(list.get(position));
            //  Uri
          //  avatarImageView.setImageURI(list.get(position));
//              avatarImageView.setImageBitmap(list.get(position));

         //   avatarImageView.setImageURI();


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView avatarImageView;
            ImageView likeImageView;
            ImageView dislikeImageView;

            MyViewHolder(View itemView) {
                super(itemView);
                avatarImageView = (ImageView) itemView.findViewById(R.id.iv_avatar);
                likeImageView = (ImageView) itemView.findViewById(R.id.iv_like);
                dislikeImageView = (ImageView) itemView.findViewById(R.id.iv_dislike);
            }

        }
    }



}
