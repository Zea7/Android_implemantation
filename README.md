# 브링Bring  


## 팀원  
- 정민근, KAIST 19, 전산학부
- 최윤정, 숙명여대 19, 컴퓨터과학전공  

# 기본적인 구성  
<img src="https://user-images.githubusercontent.com/49242646/148054090-300991dc-dd5f-446f-9287-9678d88f869a.gif" width="205" height="411"/>  
+ ### Splash Screen  
앱을 실행하면 앱 이름(브링Bring)을 보여주는 Splash Screen이 2초 동안 뜨고 MainActivity로 넘어간다.

+ ### **Tab layout & ViewPager 2**  
MainActivity는 3개의 tab을 가지는 Tab layout이다.  
여기에 VPAdapter(@FragmentStateAdapter)를 사용하여 ViewPager 2를 연결해주어 Tab layout과 Viewpager를 동시에 사용하였다.

+ ### 권한 허용  
앱에서 보여지는 연락처, 사진, 음악은 전부 External Storage에서 가져온다.  
즉, 휴대폰 내의 저장소에 있는 정보들을 가져오는 것이기 때문에 권한 허용이 필요하다.  
TedPermission Library를 이용하여 권한 허용 요청을 보냈으며,  
사용하는 권한은 외부 저장소 사용, 외부 연락처 사용, 카메라, 녹음 허용이다.  

### TedPermission  
```Java
String[] permissions = new String[] {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("권한이 필요합니다.")
                .setDeniedMessage("[설정] > [권한] 에서 권한을 활성화해야 앱이 작동합니다.")
                .setPermissions(permissions).check();
```

+ ### 다크 모드  
휴대폰 내의 다크 모드를 누르면, 앱에 다크 모드가 적용되어 보여진다.  


# Tab


+ 모듈화
  + 각 탭별로 분할하여 따로 작업하였기에, 각각의 탭을 별개의 application으로 만들어 테스트한 후,  
  + Library로 바꾸어 app 내의 MainActivity와 연결하여 합쳤다.  
  + 이 때 TabLayout과 ViewPager2를 연결하였기 때문에  
  + 각 탭별로 fragment를 만들어 해당 fragment와 각 모듈의 MainAcivity를 method로 만들어준 Util class를 통해  
  + 하나의 앱으로 합쳤다.  


<img src="https://user-images.githubusercontent.com/49242646/148053927-3bb09ee2-24ff-4142-bb52-c15f71270bde.gif" width="205" height="411"/>

+ ### Tab 1 연락처  
  + MainActivity  
     + MainActivity는 외부 저장소의 연락처를 RecyclerView를 이용하여 전부 보여준다.  
     + 각 아이템을 클릭하면 ContactActivity2를 호출하여 연락처가 가지고 있는 사진, 이름, 전화번호를 크게 보여준다.  
     + SwipeRefresh가 적용되어 있어 연락처의 추가, 수정, 삭제 등 변화가 생기면 swipe refresh를 통해 바로 확인할 수 있다.  
     + '+' 버튼은 클릭하면 새로운 연락처를 추가하는 AddContact Activity를 호출한다.  
     + 추가와 삭제, 수정은 ContentProviderOperation을 통해 외부 저장소의 연락처를 SQL 형태로 접근, 변경해주었다.  


  + ContactActivity2  
    + ContactActivity2는 연락처의 정보를 크게 보여주며 상단 메뉴에 삭제, 수정 메뉴를 포함하고 있다.  
    + 삭제를 클릭하면 해당 연락처를 외부 저장소에서 삭제하며  
    + 수정을 클릭할 경우 해당 연락처를 수정하여 외부 저장소에 변화를 저장하는 ChangeContact Activity를 호출한다.  


  + AddContact Activity  
    + AddContact Activity는 새로운 연락처를 추가하여 저장할 수 있는 Activity이다.
    + 아이콘을 클릭하여 이미지를 불러와 해당 연락처의 사진으로 저장할 수 있으며,  
    + 이름 전화번호를 추가하여 저장할 수 있다.  


  + ChangeContact Activity
    +  ChangeContact Activity는 존재하는 연락처의 정보를 수정하는 Activity이다.  
    +  아이콘, 이름, 전화번호를 수정할 수 있으며 수정하게 되면 이전에 보던 화면은 없애고  
    +  수정된 정보가 저장된 새로운 ContactActivity2를 호출하여 변경된 정보를 보여준다.  



+ ### Tab 2 사진첩  

  + MainActivity  
    +  연락처와 마찬가지로 RecyclerView를 (GridLayout)을 이용하여 4개의 column으로 외부 저장소 내의 모든 사진을 보여준다.  
    +  사진은 MediaStore를 통하여 불러왔다. 
    +  각 아이템을 클릭하면 확대하여 사진을 보여주는 TouchActivity를 호출하며,  
    +  카메라 이미지 버튼을 클릭하면 휴대폰 내의 기본 카메라 앱을 호출하는 CamerActivity를 호출한다.    
    +  찍은 사진은 바로 외부 저장소에 저장되며 연락처와 마찬가지로 SwipeRefresh가 적용되어 있기 때문에  
    +  변경된 부분은 바로 확인할 수 있다.  
 
 
  + TouchAcitivity  
    +  TouchActivity는 사진을 확대하여 보여주며  
    +  PhotoView를 통하여 보여주기 때문에 두 손가락 드래그를 통한 확대, 축소가 가능하다.  
    +  ViewPager2를 연결해주는 ImageFragmentAdapter를 연결해두었기에 옆으로 넘기며 다른 사진을 보는 것이 가능하며,  
    +  팝업 메뉴 버튼을 누르면 사진의 이름을 확인할 수 있는 상세 정보 확인 메뉴와 삭제할 수 있는 삭제 메뉴가 뜬다.  
    +  상세 정보 확인은 사진의 이름을 Toast를 통해 띄워주고  
    +  삭제를 클릭하면 권한을 요청한 뒤, 삭제를 요청하면 삭제되고 activity가 종료된다.  
  
  
  + CamerActivity   
    + CamerActivity는 기본 카메라 앱을 호출하여 사진을 찍으며, 사진을 저장하면 종료된다.  
    + 저장한 사진은 외부 저장소에 저장되기 때문에 메인 화면에서 refresh를 하여 확인할 수 있다.

<img src="https://user-images.githubusercontent.com/49242646/148055118-4b19e9f2-3532-4967-a333-6da336fc9f36.gif" width="205" height="411"/>




+ ### Tab 3 음악 플레이어  

  + MainActivity  
    + 위와 마찬가지로 recyclerView를 통해 모든 음악 파일을 보여주며 앨범 이미지, 노래 제목, 아티스트 이름을 띄워준다. 
    + 음악은 MediaStore를 통하여 불러왔다.   
    + 각 아이템을 클릭하면 노래를 듣거나 넘기는 등의 기능이 존재하는 PlayerActivity를 호출한다.  

  + PlayerActivity  
    + PlayerActivity는 노래의 제목, 아티스트, 앨범 이미지를 보다 크게 보여주며 실제로 노래를 재생해주는 activity이다.  
    + 5개의 버튼을 통해 상호작용할 수 있으며, 왼쪽부터 차례대로 10초 전으로 가기, 이전 노래로 넘기기, 멈춤/재생, 다음 노래로 넘기기, 10초 뒤로 가기이다.  
    + 노래를 처음 클릭하거나 다른 노래로 넘길 때마다 앨범 이미지가 360도 회전하며  
    + audiovisualizer 라이브러리를 이용하여 소리의 음량을 가시화하여 하단에 띄워준다.  

<img src="https://user-images.githubusercontent.com/49242646/148055582-62e0f0b1-f1a6-4516-8f34-76e3f69d2b9c.gif" width="205" height="411"/>


+ ### Font  
        완도희망체 Bold  
        프리텐다드   
        카페24 써라운드에어  
