new Vue({
    el: '#app',
    data: function () {
        return {
            activeIndex:'3-3',
            activeName: 'Model Item',

            databrowser:[],
            dataid:'',
            searchcontent:'',
            thisciteurl:'',
            comforcom:false,
            comments:false,
            comforcomtextarea:'',
            mycommentforthedata:'',
            showkey:'',

            allcomments:[],
            thumbs:'',
            thisthumbs:'',

            dialogVisible:false,
            dialogShowClose:false,
            contentBeforeDeploy:true,
            contentDeploying:false,
            contentAfterDeploy_suc:false,
            contentAfterDeploy_fail:false,
            footerBeforeDeploy:true,
            footerAfterDeploy:false,

        }
    },
    methods: {
        deploy(){
            this.contentBeforeDeploy=false;
            this.contentDeploying=true;
            this.footerBeforeDeploy=false;

            const hrefs=window.location.href.split("/");
            const oid=hrefs[hrefs.length-1];
            console.log(oid)
            $.ajax({
                type: "POST",
                url: "/computableModel/deploy/"+oid,
                data: {},
                async: true,
                success: (json) => {
                    setTimeout(() => {
                        this.contentDeploying = false;
                        if (json.code == 0) {
                            this.contentAfterDeploy_suc = true;
                        }
                        else {
                            this.contentAfterDeploy_fail = true;
                        }
                        this.footerAfterDeploy = true;
                    },500)
                }
            })
        },
        invoke(){
            const href=window.location.href;

            const hrefs=href.split("/");
            console.log(hrefs);
            window.open("/task/"+hrefs[hrefs.length-1]);
        },


        handleDownload(index,row){
            // console.log(index,row);
        },
        handleShare(index,row){

        },
        getImg(item){
            return "/static/img/filebrowser/"+item.suffix+".svg"
        },

        generateId(key){
            return key;
        },
        getid(eval){
            alert(eval);
            this.dataid=eval;


        },

        share(){
            // if(this.dataid==''){
            //     this.$message('please select file first!!');
            // }

            let item =this.databrowser[this.dataid]

            if(item!=null){
                let url ="/dataItem/downloadRemote?fileName="+item.fileName+"&sourceStoreId="+item.sourceStoreId+"&suffix="+item.suffix;
                this.$alert("<input style='width: 100%' value="+url+">",{
                    dangerouslyUseHTMLString: true
                })
                // this.dataid='';

            }else {
                // console.log("从后台获取数据条目数组有误")
                this.$message('please select file first!!');
            }
        },
        //批量下载还有问题，待修改
        dall(){


            let locaurl=window.location.href;
            let url =locaurl.split("/");
            // console.log(url[url.length-1]);

            let downloadallzipurl="http://localhost:8081/dataResource/downloadAll/"+url[url.length-1];

            let link =document.createElement("a");
            link.style.display='none';
            link.href=downloadallzipurl;
            link.setAttribute("download","alldata.zip");

            document.body.appendChild(link);
            link.click();

        },
        showtitle(ev){
            return ev.fileName+"\n"+"Type:"+ev.suffix;
        },
        downloaddata(){

            // if(this.dataid==undefined){
            //     this.$message('please select file first!!');
            // }
            // console.log("downid"+this.dataid)

            let item =this.databrowser[this.dataid];

            if(item!=null){
                let url ="/dataItem/downloadRemote?fileName="+item.fileName+"&sourceStoreId="+item.sourceStoreId+"&suffix="+item.suffix;
                let link =document.createElement('a');
                link.style.display='none';
                link.href=url;
                link.setAttribute(item.fileName,'filename.'+item.suffix)

                document.body.appendChild(link)
                link.click();

            }else {
                this.$message('please select file first!!');
            }


        },

        showsearchresult(data){

            //动态创建DOM节点

            for(let i=0;i<this.databrowser.length;i++){
                //匹配查询字段
                if(this.databrowser[i].fileName.toLowerCase().indexOf(data.toLowerCase())>-1){
                    //插入查找到的card

                    //card
                    let searchresultcard=document.createElement("div");
                    searchresultcard.classList.add("el-card");
                    searchresultcard.classList.add("dataitemisol");
                    searchresultcard.classList.add("is-never-shadow");
                    searchresultcard.classList.add("sresult");


                    //cardbody
                    let secardbody=document.createElement("div");
                    secardbody.classList.add("el-card__body");
                    //card里添加cardbody
                    searchresultcard.appendChild(secardbody);

                    //el-row1
                    let cardrow1=document.createElement("div");
                    cardrow1.classList.add("el-row");
                    secardbody.appendChild(cardrow1);

                    //3个div1
                    //div1
                    let div1=document.createElement("div");
                    div1.classList.add("el-col");
                    div1.classList.add("el-col-6");

                    let text1=document.createTextNode(" ");
                    div1.appendChild(text1);

                    cardrow1.appendChild(div1)

                    //div2
                    let div2=document.createElement("div");
                    div2.classList.add("el-col");
                    div2.classList.add("el-col-12");

                    let img=document.createElement("img");
                    img.src="/static/img/filebrowser/"+this.databrowser[i].suffix+".svg";

                    img.style.height='60%';
                    img.style.width='100%';
                    img.style.marginLeft='30%';

                    div2.appendChild(img);

                    cardrow1.appendChild(div2)

                    //div3
                    let div3=document.createElement("div");
                    div3.classList.add("el-col");
                    div3.classList.add("el-col-6");

                    let text2=document.createTextNode(" ");
                    div3.appendChild(text2);

                    cardrow1.appendChild(div3);


                    //el-row2
                    let cardrow2=document.createElement("div");
                    cardrow2.classList.add("el-row");
                    secardbody.appendChild(cardrow2);

                    //3个div2
                    //div4
                    let div4=document.createElement("div");
                    div4.classList.add("el-col");
                    div4.classList.add("el-col-2");

                    let text3=document.createTextNode(" ");
                    div4.appendChild(text3);

                    cardrow2.appendChild(div4)

                    //div5
                    let div5=document.createElement("div");
                    div5.classList.add("el-col");
                    div5.classList.add("el-col-20");

                    let p=document.createElement("p");
                    div5.appendChild(p);

                    let filenameandtype=document.createTextNode(this.databrowser[i].fileName+'.'+this.databrowser[i].suffix);
                    p.appendChild(filenameandtype)

                    cardrow2.appendChild(div5)

                    //div6
                    let div6=document.createElement("div");
                    div6.classList.add("el-col");
                    div6.classList.add("el-col-20");

                    let text4=document.createTextNode(" ");
                    div6.appendChild(text4);

                    cardrow2.appendChild(div6)

                    //往contents里添加card
                    document.getElementById("browsercont").appendChild(searchresultcard);

                    //DOM2级事件绑定

                    // searchresultcard.addEventListener('click',()=>{
                    //    //点击赋值id
                    //     this.dataid=i;
                    // });
                    searchresultcard.click(function () {
                        this.dataid=i;
                    })

                }
            }
        },
    },
    mounted(){
        $(document).on("click", ".detail-toggle", function () {
            if ($(this).text() == "[Collapse]") {
                $(this).text("[Expand]");
            }
            else {
                $(this).text("[Collapse]")
            }

        })

        new QRCode(document.getElementById("qrcode"), {
            text: window.location.href,
            width: 200,
            height: 200,
            colorDark : "#000000",
            colorLight : "#ffffff",
            correctLevel : QRCode.CorrectLevel.H
        });
    }
})