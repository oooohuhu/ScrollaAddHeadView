# ScrollaAddHeadView
##自定义ScrollView 支持添加头部并且对头部ImageView支持放大缩小，上滑头部缩小，下滑头部显示放大
使用方式：
        scrollView = (MyScrollView) findViewById(R.id.scrollview);
        headerView = findViewById(R.id.lay_header);
        imgView = (ImageView) findViewById(R.id.civ_avatar);
        
        scrollView.setHeaderView(headerView, imgView);
