package com.example.qq;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.qq.adapter.ChatAdapter;
import com.example.qq.adapter.FaceVPAdpater;
import com.example.qq.adapter.Rec_FaceAdapter;
import com.example.qq.adapter.Rec_FaceListAdapter;
import com.example.qq.apps.MyApp;
import com.example.qq.bean.ChatMsgBean;
import com.example.qq.common.Message;
import com.example.qq.face.FaceListItemVo;
import com.example.qq.face.FaceTabVo;
import com.example.qq.utils.SmileyParser;
import com.example.qq.utils.SpUtils;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView mTv;
    private Button mBtn;
    private Context context;
    private List<FaceTabVo> tabList;
    private Rec_FaceAdapter rec_faceAdapter;
    private ArrayList<Fragment_face> fragment_faces;
    private String uid;
    List<ChatMsgBean> msgList; //消息列表
    StringBuilder chatInput;
    private ChatAdapter chatAdapter;


    @BindView(R.id.fini)
    TextView fini;

    @BindView(R.id.edit_chat)
    EditText editChat;

    @BindView(R.id.img_face)
    ImageView imgFace;

    @BindView(R.id.rec)
    RecyclerView rec_chatlist;

    @BindView(R.id.text_send)
    TextView textSend;

    @BindView(R.id.face_tab)
    RecyclerView faceTab;

    @BindView(R.id.face_vp)
    ViewPager faceVp;

    @BindView(R.id.face_ConstraintLayout)
    ConstraintLayout faceConstraintLayout;

    @BindView(R.id.addFile)
    ImageView addFile;

    @BindView(R.id.file_vp)
    ViewPager fileVp;

    @BindView(R.id.file_ConstraintLayout)
    ConstraintLayout fileConstraintLayout;

    private ArrayList<Fragment_file_photo> photofragments; //相册的碎片集合
    private ArrayList<String> allImgs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        initSmailTab();
        initFileVp();
        initFaceList();
        initEdittext();
        initMsg();
        chatInput = new StringBuilder();
        uid = SpUtils.getInstance().getString("uid");
    }
//点击+显示的界面 -> 文件界面的初始化
    private void initFileVp() {
        photofragments = new ArrayList<>();
        allImgs = new ArrayList<>();
        Fragment_file_photo photoFragment = Fragment_file_photo.getInstance();
        photoFragment.addPhotoSelectListener(new Fragment_file_photo.PhotoSelect() {
            @Override
            public void selected(List<LocalMedia> list) {
                sendImageChat(list);
            }
        });
        photofragments.add(photoFragment);

        fileVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return photofragments.get(position);
            }

            @Override
            public int getCount() {
                return photofragments.size();
            }
        });
    }

    // 发送图片
    private void sendImageChat(List<LocalMedia> list) {
        for(LocalMedia item : list){
            //图片上传
            upLoading(item.getPath());

            ChatMsgBean chatMsgBean = new ChatMsgBean();
            chatMsgBean.setFromUid(uid);
            chatMsgBean.setStatus(Message.MSG_STATUS_ASYNC);
            //自己能看的本地图片地址
            chatMsgBean.setContent(item.getPath());
            chatMsgBean.setMsgType(Message.MSG_TYPE_IMAGE);
            int time = (int) (new Date().getTime()/1000);
            chatMsgBean.setTime(time);
            msgList.add(chatMsgBean);
            chatAdapter.notifyDataSetChanged();
            //图片上传
            //uploadImage(item.getPath());
        }

    }

    private void upLoading(String path) {
        //4步,记核心: okhttpclient.newCall().enqueue()
        OkHttpClient okHttpClient = new OkHttpClient();
        File file = new File(path);
        //MediaType type = MediaType.parse("application/octet-stream");
        MediaType type = MediaType.parse("image/jpg");
        if (file.exists()) {

            RequestBody body = RequestBody.create(type, file);
            RequestBody multiBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("key", "xts")//设置上传图片的文件夹
                    .addFormDataPart("file", file.getName(), body)
                    .build();
            final Request request = new Request.Builder()
                    .post(multiBody)
                    .url("http://yun918.cn/study/public/file_upload.php")
                    .build();
            Call call = okHttpClient.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("TAG", "onFailure: " + e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d("TAG", "onResponse: " + response.body().string());
                    String string = response.body().string();

                }
            });
        }
    }


    //初始化消息列表
    private void initMsg() {
        msgList = new ArrayList<>();
        chatAdapter = new ChatAdapter(msgList, this);
        rec_chatlist.setLayoutManager(new LinearLayoutManager(this));
        rec_chatlist.setAdapter(chatAdapter);

        chatAdapter.setOnclick(new ChatAdapter.OnchatlistImg() {
            @Override
            public void Onclick(List<ChatMsgBean> list) {
                showPop(list);
            }
        });
    }

    //监听输入框
    private void initEdittext() {
        editChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(editChat.getText().toString())) {
                    if (textSend.getVisibility() == View.GONE) {
                        textSend.setVisibility(View.VISIBLE);
                        addFile.setVisibility(View.GONE);
                    }
                } else {
                    textSend.setVisibility(View.GONE);
                    addFile.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    // vp 的设置
    private void initFaceList() {
        fragment_faces = new ArrayList<>();
        int size = SmileyParser.getInstance(this).getFaceListSize();
        for (int i = 0; i < size; i++) {
            Fragment_face facefragment = Fragment_face.getInstance(i, new Rec_FaceListAdapter.ListClick() {
                @Override
                public void onListClick(FaceListItemVo itemVo) {
                    if (itemVo.getFaceType() == SmileyParser.FACE_TYPE_1) {
                        addSmiley(itemVo);
                    } else if (itemVo.getFaceType() == SmileyParser.FACE_TYPE_2) {
                        //发送表情
                    }
                }
            });
            fragment_faces.add(facefragment);
        }

        FaceVPAdpater faceVPAdpater = new FaceVPAdpater(getSupportFragmentManager(), fragment_faces);
        faceVp.setAdapter(faceVPAdpater);
    }

    //初始化 表情tab导航界面
    private void initSmailTab() {
        tabList = SmileyParser.getInstance(this).getFaceTabList();
        faceTab.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rec_faceAdapter = new Rec_FaceAdapter(tabList, this);
        faceTab.setAdapter(rec_faceAdapter);
        rec_faceAdapter.setOnFaceClick(new Rec_FaceAdapter.OnFaceClick() {
            @Override
            public void Onclick(int position) {
                faceVp.setCurrentItem(position);
            }
        });
    }


    @OnClick({R.id.fini, R.id.img_face, R.id.text_send, R.id.addFile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fini:
                finish();
                break;
            case R.id.img_face:
                showConstraintLayout();
                break;
            case R.id.text_send:
                sendMsg();
                break;
            case R.id.addFile:
                showFileConstraintLayout();
                break;
        }
    }

    private void showFileConstraintLayout() {
        if (faceConstraintLayout.getVisibility()== View.VISIBLE){
            faceConstraintLayout.setVisibility(View.GONE);
        }
        fileConstraintLayout.setVisibility(fileConstraintLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    private void sendMsg() {
        String content = editChat.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
        }
        int own = (int) (Math.random() * 3);
        String _uid = own == 1 ? uid : "200";
        //构建消息对象
        ChatMsgBean chatMsgBean = new ChatMsgBean();
        chatMsgBean.setFromUid(_uid);
        chatMsgBean.setContent(content);
        chatMsgBean.setMsgType(Message.MSG_TYPE_WORD);
        int time = (int) (new Date().getTime() / 1000);
        chatMsgBean.setTime(time);
        msgList.add(chatMsgBean);
        //清空输入框
        editChat.setText("");
        //chatInput.delete(0, chatInput.length());
        chatAdapter.notifyDataSetChanged();
    }

    private void showConstraintLayout() {
        if (fileConstraintLayout.getVisibility()== View.VISIBLE){
            fileConstraintLayout.setVisibility(View.GONE);
        }
        faceConstraintLayout.setVisibility(faceConstraintLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    /**
     * 插入表情
     *
     * @param itemVo
     */
    private void addSmiley(FaceListItemVo itemVo) {
        String str = null;
        int selectPos;
        //获取editChat光标所在的位置
        int start = editChat.getSelectionStart();
        //在输入文本后面加入表情
        if (editChat.getText().length() == 0 || start >= editChat.getText().toString().length()){
            str = editChat.getText().toString()+itemVo.getTag();
            selectPos = str.length();//确定光标的位置
            //在输入文本前面加入表情
        }else if (start == 0 && editChat.getText().length()>0){
            str = itemVo.getTag() + editChat.getText().toString();
            selectPos = itemVo.getTag().length();
        }else {
            String them = editChat.getText().toString();
            String str1 = them.substring(0, start);
            String str2 = them.substring(start, them.length());
            str = str1 + itemVo.getTag() + str2;
            selectPos = str1.length() - str2.length();
        }

        CharSequence chat = SmileyParser.getInstance(MyApp.myApp).replace(str);

        editChat.setText(chat);
        //设置光标的位置
        editChat.setSelection(selectPos);
    }

    private void showPop(List<ChatMsgBean> list) {

        allImgs = new ArrayList<>();
        ArrayList<Fragment> chatlistphotos = new ArrayList<>();

        int size = list.size();
        for (int i = 0; i < size; i++) {
            ChatMsgBean chatMsgBean = list.get(i);
            //判断是否是图片类型
            if (chatMsgBean.getMsgType() == Message.MSG_TYPE_IMAGE){
                //获取图片资源
                String content = chatMsgBean.getContent();
                Fragment_chatlist_photo photo = Fragment_chatlist_photo.getInstance(content);
                allImgs.add(content);
                chatlistphotos.add(photo);
            }
        }

        View view = LayoutInflater.from(context).inflate(R.layout.pop_photo, null);
        PopupWindow pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.showAtLocation(rec_chatlist, Gravity.CENTER,0,0);

        ViewPager popVp = view.findViewById(R.id.pop_vp);

        TextView num = view.findViewById(R.id.pop_num);

       /* popVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return chatlistphotos.get(position);
            }

            @Override
            public int getCount() {
                return chatlistphotos.size();
            }
        });*/

    }
}
