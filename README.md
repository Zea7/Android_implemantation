# 몰입캠프 1주차 (12.28 ~ 01.04)
Android implementation

# 참가자
- 정민근, KAIST 19, 전산학부
- 최윤정, 숙명여대 19, 컴퓨터과학전공

# 기본적인 구성
## Tab layout & ViewPager 2
MainActivity는 3개의 tab을 가지는 Tab layout이다.  
여기에 VPAdapter(@FragmentStateAdapter)를 사용하여 ViewPager 2를 연결해주어 Tab layout과 Viewpager를 동시에 사용하였다.

## 권한 허용
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

# Tab

## Tab 1 연락처  

휴대폰 외부 저장소의 연락처 목록을 전부 받아와 오름차순으로 보여준다.  
연락처를 받아올 때 외부 저장소에 저장된 프로필 사진을 가져와 아이콘에 보여준다.  
만약 저장된 사진이 없다면 다음과 같은 사진을 보여준다.  
![person](https://user-images.githubusercontent.com/68819204/147920890-da2d34da-ee85-4e75-8b41-e7ad9a857178.png)  

------------

연락처는 RecyclerView를 이용하여 보여주었으며 RecyclerView에 연결되는 각 아이템에는 프로필 사진과 이름, 전화번호를 보여준다. 
### 사진 추가 
여기에 FrameLayout을 사용하여 아이템 전체를 감싸는 투명 버튼을 위에 올려주어 아이템의 어떤 부분을 터치하든 상관없이 상세 정보 창으로 연결되도록 하였다.  

> 연락처 영역을 클릭하여 상세정보창을 띄우면 다음과 같은 형태로 창이 뜬다.
### 사진추가
> 사진 영역은 연락처 리스트를 보여주었을 때와 마찬가지로 프로필 사진을 보여주며  
> 이름, 전화번호를 보다 크게 보여준다
> 추가, 삭제, 수정 기능 

## Tab 2 사진첩
휴대폰 외부 저장소의 이미지를 전부 받아와 내림차순으로 보여준다.  
이미지가 외부 저장소에서 변경된 마지막 시간을 기준으로 내림차순하는 것이기 때문에 이름과 관계없이 보여진다.  
```Java
cursor = view.getContext().getContentResolver().query(uri, projection, null, null, MediaStore.MediaColumns.DATE_TAKEN + " DESC");
```

사진첩도 RecyclerView를 사용하여 보여주었으며 각 아이템은 사진을 centerCrop을 통해 아이템 fitting box 내에 scaling하여 보여준다.  
### 사진 추가  
카메라 이미지 버튼을 클릭하면 휴대폰 기본 카메라 앱을 불러와 사진을 찍을 수 있는 기능이 구현되어 있으며 찍은 사진은 바로 외부 저장소에 저장된다.  
저장하거나 혹은 삭제한 사진은 바로 적용되지는 않지만 SwipeRefreshLayout이 적용되어 있기 때문에 swipe를 통해 리스트를 refresh하여 확인할 수 있다.  


> 각 아이템을 클릭하게 되면 다음과 같은 형태로 확대하여 보여주며 PhotoView Library를 사용하여 손으로 드래그를 통해 확대 축소가 가능하다.  
> ### 사진 추가
> App bar 위치에 뒤로가기 버튼과 메뉴 버튼을 구현하였으며 메뉴 버튼을 누르면 상세 정보 확인과 삭제할 수 있는 메뉴가 나타난다.
> 상세 정보 확인을 누르면 사진의 이름이 Toast message 형태로 나타난다.  
> 삭제를 누르면 permission이 요청되며 실제로 사진을 삭제할 수 있다. 삭제를 취소하면 activity가 끝나지 않지만 삭제를 하게 될 경우에는 자동으로 activity가 종료된다.  



## Tab 3 음악 플레이어
휴대폰 외부 저장소의 음악 목록을 전부 받아와 오름차순으로 보여준다.  
음악을 받아올 때 외부 저장소에 저장된 앨범 사진을 가져와 아이콘에 보여준다.  
만약 저장된 앨범 사진이 없다면 다음과 같은 사진을 보여준다.  
![music_note_black](https://user-images.githubusercontent.com/49242646/147929757-a1e0b9fd-2ad3-46b4-b975-23edde330501.png)

------------

음악 플레이어는 RecyclerView를 이용하여 보여주었으며 RecyclerView에 연결되는 각 아이템에는 앨범 사진과 곡 제목을 보여준다. 
<img src="https://user-images.githubusercontent.com/49242646/147933525-e7d4076e-f44a-4c01-be78-1500433fedef.jpg" width="205" height="412"/>
<img src="https://user-images.githubusercontent.com/49242646/147933917-b276adb9-0a42-4126-999d-a33a5141c641.jpg"  width="205" height="412"/>

여기에 FrameLayout을 사용하여 아이템 전체를 감싸는 투명 버튼을 위에 올려주어 아이템의 어떤 부분을 터치하든 상관없이 노래 재생 창으로 연결되도록 하였다.  

> 음악 아이템 클릭하면, 다음과 같은 형태로 음악 재생 창이 뜨고 노래가 바로 재생되며 앨범 사진이 360도 회전한다.  
> 사진 영역은 음악 플레이어 리스트를 보여주었을 때와 마찬가지로 앨범 사진을 보여주며  
> 곡 제목을 보다 크게 보여준다  
<img src="https://user-images.githubusercontent.com/49242646/147934803-5dd0b5ea-e77e-4dba-940f-13b07b2e6490.gif"  width="205" height="412"/>

> 1. fast_rewind 버튼을 누르면 10초 전으로 이동한다.
> 2. skip_prev 버튼을 누르면 이전 곡을 재생한다.
> 3. play_arrow 버튼을 누르면 pause 아이콘으로 바뀌고 곡 재생이 멈추며, pause 버튼을 누르면 play_arrow 아이콘으로 바뀌고 곡이 재생된다.
> 4. skip_next 버튼을 누르면 다음 곡을 재생한다.
> 5. fast_forward 버튼을 누르면 10초 후로 이동한다.

> audiovisualizer 라이브러리를 이용하여 소리의 음량에 따라 그래프를 보여준다.
