/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osproject;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Dell
 */
public class VirtualMemorySimulation_GUI extends javax.swing.JFrame {
     public int numOfPages=0;
    public int frame=0;
   public String randomReferenceList[];
    
    
      public  void FIFO(){
          
           jTextArea1.append("\n\n.....................FIFO................\n\n");
          
        int referenceLength,num=0, pageHit=0,diskAccesses=0,pageFaults=0;
        String buffer[], victim[],referenceList[];
        boolean flag = true;
        boolean hit[];
     
        buffer = new String[frame];
      
      
        for(int i=0; i<frame; i++)
        {
            buffer[i] = "  ";
        }
      
        referenceList=new String[randomReferenceList.length] ;
        referenceLength = referenceList.length;
        System.arraycopy(randomReferenceList, 0,referenceList, 0, referenceList.length); 
           
            victim= new String[referenceLength];
           hit = new boolean[referenceLength];
        for(int i=0; i<referenceLength; i++)
        {
            flag = true;
            //int page = pages[i];
            for(int j=0; j<frame; j++)
            {
                 if(buffer[j].charAt(1) == referenceList[i].charAt(1))
                {
                  buffer[j]=referenceList[i];
                    flag = false;
                    pageHit++;
                    hit[i] = true;
                    break;
                }
            }
            if(num == frame)
                num = 0;
          
            if(flag)
            {
             String string = buffer[num];
            String substring = "W";
           if(string.contains(substring)){ 
              diskAccesses++;
           }
                
                
                victim[i]=buffer[num];
                buffer[num] = referenceList[i];
                pageFaults++;
                num++;
            }
            
            if(hit[i]){
                jTextArea1.append(referenceList[i]+":\t");
            for(int k=0; k<frame; k++)
               jTextArea1.append(" "+"\t");
               jTextArea1.append("|HIT" );
               jTextArea1.append("\n");
                
            }else
            {
            jTextArea1.append(referenceList[i]+":\t");
            for(int k=0; k<frame; k++)
               jTextArea1.append(buffer[k]+"\t");
               if(! victim[i].equals("  ")){
                if( victim[i].contains("W"))
              jTextArea1.append("|Page Fault=" +pageFaults +" |"+" victim: "+victim[i]+" |Dirty bit= "+ "1");
             else
          jTextArea1.append("|Page Fault=" +pageFaults +" |"+" victim: "+victim[i]+" |Dirty bit= "+ "0");  
               }else{
                   jTextArea1.append("|Page Fault=" +pageFaults);
               }   
               
     
               
            jTextArea1.append("\n");
             
            }
        } 
        
        // jTextArea1.append("\n\n");
      jTextArea1.append("\nNumber of Page Faults : "+pageFaults+"\n");
      jTextArea1.append("Number of Page Hit : "+pageHit+"\n");
      jTextArea1.append("Number of Disk Accesses : "+diskAccesses+"\n");
    }
          
        public  void OPTIMAL(){
         
        jTextArea1.append("\n\n..............Optimal Algorthim..............\n\n");  
        int pointer = 0,pageFaults = 0, dirtyBit =-1,referenceLength, numHITs;
        boolean isFull = false;
        boolean[] Hit;
        int[] Fault;
        String referenceList[], MemoryTabel[][],victim[],buffer[]; 
        int diskAccesses=0;
        
        referenceList=new String[randomReferenceList.length] ;
        referenceLength = referenceList.length;
        System.arraycopy(randomReferenceList, 0,referenceList, 0, referenceList.length); 
       
        victim=new String[referenceLength];
        MemoryTabel = new String[referenceLength][frame];
        buffer = new String[frame];
        Hit = new boolean[referenceLength];
        Fault = new int[referenceLength];
        
        
        for(int j = 0; j < frame; j++)
        {
            buffer[j] = "  ";
        }

        
      
        //System.out.println();
        for(int i = 0; i < referenceLength; i++)
        {
            int search = -1;
            for(int j = 0; j < frame; j++)
            {
                if(buffer[j].charAt(1) == referenceList[i].charAt(1))
                {
                  buffer[j]=referenceList[i];
                    search = j;
                    Hit[i] = true;
                    Fault[i] = pageFaults;
                    break;
                }
            }

            if(search == -1)
            {
                if(isFull)
                {
                    int[] pointer2 = new int[frame];
                    boolean index_flag[] = new boolean[frame];
                    for(int j = i + 1; j < referenceLength; j++)
                    {
                        for(int k = 0; k < frame; k++)
                        {
                            if((referenceList[j].charAt(1) == buffer[k].charAt(1)) && (index_flag[k] == false))
                            {
                                pointer2[k] = j;
                                index_flag[k] = true;
                                break;
                            }
                        }
                    }
                    int flagMax = pointer2[0];
                    pointer = 0;
                    if(flagMax == 0)
                    {
                        flagMax = 200;
                    }

                    for(int j = 0; j < frame; j++)
                    {
                        if(pointer2[j] == 0)
                        {
                            pointer2[j] = 200;
                        }

                        if(pointer2[j] > flagMax)
                        {
                            flagMax = pointer2[j];
                            pointer = j;
                        }
                    }
                }
                
               

            String substring = "W";
           if(buffer[pointer].contains(substring)){
              diskAccesses++;
           }
           
               victim[i]= buffer[pointer];
               
                buffer[pointer] = referenceList[i];
                pageFaults++;
                Fault[i] = pageFaults;
                if(!isFull)
                {
                    pointer++;
                    if(pointer == frame)
                    {
                        pointer = 0;
                        isFull = true;
                    }
                }
            }

            for(int j = 0; j < frame; j++)
            {
                MemoryTabel[i][j] = buffer[j];
            }
        }

        for(int i = 0; i < referenceLength; i++)
        {   
            
         
             jTextArea1.append(referenceList[i] + ":\t");
            for(int j = 0; j < frame; j++)
            {
                if (MemoryTabel[i][j] == " ")
                {
                    jTextArea1.append(" "+"\t");
                } else
                {
                   if(!Hit[i])
                    jTextArea1.append(MemoryTabel[i][j]+"\t");
                   else
                    jTextArea1.append(" "+"\t");   
                }
            }
           // jTextArea1.append(": ");
            if (Hit[i]) {
                jTextArea1.append("Hit");
            } else
            {
               if(victim[i].equals("  ")){
                jTextArea1.append("Page Fault= " + Fault[i] );
               }
               else{
                   
                   if( victim[i].contains("W")){
                jTextArea1.append("Page Fault= " + Fault[i]+"|" );
               jTextArea1.append(" Victim Is:"+victim[i]+"| Dirty bit= "+ "1");
                   }else{
               jTextArea1.append("Page Fault=" + Fault[i]+"|" );
                jTextArea1.append(" Victim Is:"+victim[i]+"| Dirty bit= "+ "0"); 
                   }
               }
            }
            jTextArea1.append("\n");
        }
        numHITs= referenceList.length-pageFaults;
        jTextArea1.append("\nNumber of Page Faults: " + pageFaults+"\n");
        jTextArea1.append("Number of Page HITs: " + numHITs+"\n");
        jTextArea1.append("Number of disk accesses: " + diskAccesses+"\n");
             
        }
    public  void LRU(){
        
       jTextArea1.append("\n\n..............LRU Algorthim..............\n\n");  
        int pointer = 0;
        int fault = 0;
        int diskAccesses=0;
        int referenceLength = 0;
        Boolean full = false;
        String mem[];
        String victim[];
        ArrayList<String> stack = new ArrayList<String>();
        String[] referenceList=new String[randomReferenceList.length];

      
       referenceList=new String[randomReferenceList.length] ;
       referenceLength = referenceList.length;
        System.arraycopy(randomReferenceList, 0,referenceList, 0, referenceList.length); 

        referenceLength=referenceList.length;
          victim= new String[referenceLength];

  
      
        mem = new String[frame];
        for(int j = 0; j < frame; j++)
            mem[j] = "  ";

        System.out.println();
        for(int i = 0; i < referenceLength; i++){
            if(stack.contains(referenceList[i])){
                stack.remove(stack.indexOf(referenceList[i]));
            }
            stack.add(referenceList[i]);
            int search = -1;
            for(int j = 0; j < frame; j++){
                if(mem[j].charAt(1) == referenceList[i].charAt(1))
                {
                  mem[j]=referenceList[i];
                    search = j;

                    jTextArea1.append(referenceList[i]+":\t");
                    for(int w = 0; w < frame; w++){
                        jTextArea1.append(" "+"\t");
                    }
                    jTextArea1.append("Hit: \n");
                    break;
                }
            }
            if(search == -1){
                if(full){
                    int min_loc = referenceLength;
                    for(int j = 0; j < frame; j++){     
                        if(stack.contains(mem[j])){ 
                            int temp = stack.indexOf(mem[j]);
                            if(temp < min_loc){

                                min_loc = temp;
                                pointer = j;
                            }
                        }
                    }
                }
                
               String string = mem[pointer];
            String substring = "W";
           if(string.contains(substring)){
              diskAccesses++;
           }
                
                victim[i]=mem[pointer];
                mem[pointer] = referenceList[i];
                fault++;
              jTextArea1.append(referenceList[i]+":\t");
                for(int w = 0; w < frame; w++){
                  jTextArea1.append(mem[w]+"\t");
                }
                 if(! victim[i].contains(" ")){
                 if( victim[i].contains("W")){
                jTextArea1.append("Page Fault= " + fault+"|" );
               jTextArea1.append(" Victim Is:"+victim[i]+"| Dirty bit= "+ "1");
                   }else{
               jTextArea1.append("Page Fault=" + fault+"|" );
                jTextArea1.append(" Victim Is:"+victim[i]+"| Dirty bit= "+ "0"); 
                   }
                 }else{
                     jTextArea1.append("Page Fault= " + fault ); 
                 }
                  jTextArea1.append("\n");
          
                pointer++;
                if(pointer == frame){
                    pointer = 0;
                    full = true;
                }
            }
        }
        jTextArea1.append("\nNumber of Page Faults: " + fault+"\n");  
        jTextArea1.append("Number of Page Hits: " +(referenceLength-fault)+"\n" );
        jTextArea1.append("Number of Disk Acssess: " + diskAccesses+"\n\n\n" );
    
        
        
    }
      

     public  String[] generateRandomly(int numOfPages){

        Random r = new Random();
       int l = 8;
       int h = 25;
       int Len = r.nextInt(h-l) + l;
        
        
       
         System.out.println(Len);
         Random rnd = new Random();
         String CHAR = "RW";
        String[] string1=new String[Len];
        
        
          char c;
          
          
          
       for (int i = 0; i <Len; i++) {
     c = CHAR.charAt(rnd.nextInt(CHAR.length()));
        string1[i]=String.valueOf(c);
    
    }
       String[] string=new String[Len];
        Random n = new Random();
     for (int i = 0; i < Len; i++) {
         
         
        int a=n.nextInt(numOfPages);
      
           string[i] = String.valueOf(a); 
    }
     
      for (int i = 0; i < Len; i++) {
          string1[i]=string1[i]+string[i];
   }
              
  
         return string1;
}

   
    /**
     * Creates new form VirtualMemorySimulation_GUI
     */
    public VirtualMemorySimulation_GUI() {
        initComponents();
         choice1Algorthim.add("FIFO");
         choice1Algorthim.add("OPTIMAL");
         choice1Algorthim.add("LRU");
        
         
         choice2Comparison.add("Number Of Faults");
         choice2Comparison.add("Number Of Hits");
         choice2Comparison.add("Number Of Disk Accesses");
         setResizable(false);
       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jSlider1 = new javax.swing.JSlider();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1FRAMES = new javax.swing.JTextField();
        jTextField2Pages = new javax.swing.JTextField();
        choice1Algorthim = new java.awt.Choice();
        Run = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        Enter = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        Compare = new javax.swing.JButton();
        choice2Comparison = new java.awt.Choice();
        jLabel6 = new javax.swing.JLabel();

        jMenu1.setText("jMenu1");

        jScrollPane2.setViewportView(jEditorPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(5, 129, 129));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("        Virtual Memory Simulation");

        jLabel2.setBackground(new java.awt.Color(204, 204, 255));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Number of Frames");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Number Of Pages ");

        jTextField1FRAMES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1FRAMESActionPerformed(evt);
            }
        });

        jTextField2Pages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2PagesActionPerformed(evt);
            }
        });

        choice1Algorthim.setForeground(new java.awt.Color(0, 102, 102));

        Run.setForeground(new java.awt.Color(0, 153, 153));
        Run.setText("Run");
        Run.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RunActionPerformed(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText(" Select Page Replacement Algorithms");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton3.setForeground(new java.awt.Color(0, 153, 153));
        jButton3.setText("EXIST");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        Enter.setForeground(new java.awt.Color(0, 153, 153));
        Enter.setText("Enter");
        Enter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnterActionPerformed(evt);
            }
        });

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane3.setViewportView(jTextArea2);

        Compare.setForeground(new java.awt.Color(0, 153, 153));
        Compare.setText("Compare");
        Compare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CompareActionPerformed(evt);
            }
        });

        choice2Comparison.setForeground(new java.awt.Color(0, 51, 51));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText(" Choose comparison based on");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(Enter, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(127, 127, 127))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField1FRAMES, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                                .addComponent(jTextField2Pages))))
                    .addComponent(Run, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(Compare))
                    .addComponent(jScrollPane3)
                    .addComponent(choice2Comparison, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(choice1Algorthim, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 170, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(102, 102, 102))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1FRAMES, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jButton3)))
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 7, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField2Pages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(Enter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choice1Algorthim, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(Run)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(1, 1, 1)
                        .addComponent(choice2Comparison, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Compare)
                        .addGap(14, 14, 14))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1FRAMESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1FRAMESActionPerformed
        // TODO add your handling code here:
          
    }//GEN-LAST:event_jTextField1FRAMESActionPerformed

    private void jTextField2PagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2PagesActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTextField2PagesActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
       
       System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void EnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnterActionPerformed
        // TODO add your handling code here:
        
       numOfPages= Integer.parseInt(jTextField2Pages.getText().trim());
       frame= Integer.parseInt(jTextField1FRAMES.getText().trim());
       
       String[] str=new String[1];
         randomReferenceList=generateRandomly(numOfPages);
          for (int i = 0; i < randomReferenceList.length; i++) {
              if(i==0)
              str[0]=randomReferenceList[i]+" ";
                  else
              str[0]=str[0]+randomReferenceList[i]+" ";
          }
          jTextArea1.setText(str[0]);
          jTextArea1.append("\n--------------------------------------------------------------------------------------------------------------------------------------------------\n");
     
                     
    }//GEN-LAST:event_EnterActionPerformed

    private void RunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RunActionPerformed
        // TODO add your handling code here:
        if(choice1Algorthim.getSelectedItem().equals("FIFO")){
           FIFO();
        }
        if(choice1Algorthim.getSelectedItem().equals("OPTIMAL")){
           OPTIMAL();
        }
          if(choice1Algorthim.getSelectedItem().equals("LRU")){
            LRU();
        }
            
        
        
        
    }//GEN-LAST:event_RunActionPerformed

    private void CompareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CompareActionPerformed
        // TODO add your handling code here:
        
         if(choice2Comparison.getSelectedItem().equals("Number Of Faults")){
             //fifo
             
              jTextArea2.setText(" ");
         int referenceLength,num=0, pageHit=0,diskAccesses=0,pageFaults=0;
        String buffer[], victim[],referenceList[];
        boolean flag = true;
        boolean hit[];
     
        buffer = new String[frame];
      
      
        for(int i=0; i<frame; i++)
        {
            buffer[i] = "  ";
        }
      
        referenceList=new String[randomReferenceList.length] ;
        referenceLength = referenceList.length;
        System.arraycopy(randomReferenceList, 0,referenceList, 0, referenceList.length); 
           
            victim= new String[referenceLength];
           hit = new boolean[referenceLength];
        for(int i=0; i<referenceLength; i++)
        {
            flag = true;
            //int page = pages[i];
            for(int j=0; j<frame; j++)
            {
                if(buffer[j].charAt(1) == referenceList[i].charAt(1))
                {
                  buffer[j]=referenceList[i];
                    flag = false;
                    pageHit++;
                    hit[i] = true;
                    break;
                }
            }
            if(num == frame)
                num = 0;
          
            if(flag)
            {
             String string = buffer[num];
            String substring = "W";
           if(string.contains(substring)){ 
              diskAccesses++;
           }
                
                
                victim[i]=buffer[num];
                buffer[num] = referenceList[i];
                pageFaults++;
                num++;
            }
            
      
        } 
        
      jTextArea2.append("FIFO-->Number of Page Faults : "+pageFaults);
  
      //optimal
       int pointer = 0;pageFaults = 0; int dirtyBit =-1;
          int numHITs;
        boolean isFull = false;
        boolean[] Hit;
        int[] Fault;
        String  MemoryTabel[][];
        diskAccesses=0;
        
        referenceList=new String[randomReferenceList.length] ;
        referenceLength = referenceList.length;
        System.arraycopy(randomReferenceList, 0,referenceList, 0, referenceList.length); 
       
        victim=new String[referenceLength];
        MemoryTabel = new String[referenceLength][frame];
        buffer = new String[frame];
        Hit = new boolean[referenceLength];
        Fault = new int[referenceLength];
        
        
        for(int j = 0; j < frame; j++)
        {
            buffer[j] = "  ";
        }

        
      
       
        for(int i = 0; i < referenceLength; i++)
        {
            int search = -1;
            for(int j = 0; j < frame; j++)
            {
                if(buffer[j].charAt(1) == referenceList[i].charAt(1))
                {
                  buffer[j]=referenceList[i];
                    search = j;
                    Hit[i] = true;
                    Fault[i] = pageFaults;
                    break;
                }
            }

            if(search == -1)
            {
                if(isFull)
                {
                    int[] pointer2 = new int[frame];
                    boolean index_flag[] = new boolean[frame];
                    for(int j = i + 1; j < referenceLength; j++)
                    {
                        for(int k = 0; k < frame; k++)
                        {
                            if((referenceList[j].charAt(1) == buffer[k].charAt(1)) && (index_flag[k] == false))
                            { 
                                pointer2[k] = j;
                                index_flag[k] = true;
                                break;
                            }
                        }
                    }
                    int flagMax = pointer2[0];
                    pointer = 0;
                    if(flagMax == 0)
                    {
                        flagMax = 200;
                    }

                    for(int j = 0; j < frame; j++)
                    {
                        if(pointer2[j] == 0)
                        {
                            pointer2[j] = 200;
                        }

                        if(pointer2[j] > flagMax)
                        {
                            flagMax = pointer2[j];
                            pointer = j;
                        }
                    }
                }
                
               

            String substring = "W";
           if(buffer[pointer].contains(substring)){
              diskAccesses++;
           }
           
               victim[i]= buffer[pointer];
               
                buffer[pointer] = referenceList[i];
                pageFaults++;
                Fault[i] = pageFaults;
                if(!isFull)
                {
                    pointer++;
                    if(pointer == frame)
                    {
                        pointer = 0;
                        isFull = true;
                    }
                }
            }

            for(int j = 0; j < frame; j++)
            {
                MemoryTabel[i][j] = buffer[j];
            }
        }

        for(int i = 0; i < referenceLength; i++)
        {   
            
         
            for(int j = 0; j < frame; j++)
            {
                if (MemoryTabel[i][j] == " ")
                {
                
                } else
                {
                 
                }
            }
          
        }
     
        jTextArea2.append("\nOptimal-->Number of Page Faults: " + pageFaults+"\n");
     
    
    //LRU
         pointer = 0;
        int fault = 0;
         diskAccesses=0;
         referenceLength = 0;
        Boolean full = false;
        String mem[];
       
        ArrayList<String> stack = new ArrayList<String>();
        referenceList=new String[randomReferenceList.length];

      
       referenceList=new String[randomReferenceList.length] ;
       referenceLength = referenceList.length;
        System.arraycopy(randomReferenceList, 0,referenceList, 0, referenceList.length); 

        referenceLength=referenceList.length;
          victim= new String[referenceLength];

  
      
        mem = new String[frame];
        for(int j = 0; j < frame; j++)
            mem[j] = "  ";

      
        for(int i = 0; i < referenceLength; i++){
            if(stack.contains(referenceList[i])){
                stack.remove(stack.indexOf(referenceList[i]));
            }
            stack.add(referenceList[i]);
            int search = -1;
            for(int j = 0; j < frame; j++){
                if(mem[j].charAt(1) == referenceList[i].charAt(1))
                {
                  mem[j]=referenceList[i];
                    search = j;

               
                    for(int w = 0; w < frame; w++){
                    
                    }
               
                    break;
                }
            }
            if(search == -1){
                if(full){
                    int min_loc = referenceLength;
                    for(int j = 0; j < frame; j++){     
                        if(stack.contains(mem[j])){ 
                            int temp = stack.indexOf(mem[j]);
                            if(temp < min_loc){

                                min_loc = temp;
                                pointer = j;
                            }
                        }
                    }
                }
                
               String string = mem[pointer];
            String substring = "W";
           if(string.contains(substring)){
              diskAccesses++;
           }
                
                victim[i]=mem[pointer];
                mem[pointer] = referenceList[i];
                fault++;
          
                for(int w = 0; w < frame; w++){
              
                }
                
            
                pointer++;
                if(pointer == frame){
                    pointer = 0;
                    full = true;
                }
            }
        }
        jTextArea2.append("LRU-->Number of Page Faults: " + fault+"\n");  
       
  
         
        
        }
        if(choice2Comparison.getSelectedItem().equals("Number Of Hits")){
            
             jTextArea2.setText(" ");
             int referenceLength,num=0, pageHit=0,diskAccesses=0,pageFaults=0;
        String buffer[], victim[],referenceList[];
        boolean flag = true;
        boolean hit[];
     
        buffer = new String[frame];
      
      
        for(int i=0; i<frame; i++)
        {
            buffer[i] = "  ";
        }
      
        referenceList=new String[randomReferenceList.length] ;
        referenceLength = referenceList.length;
        System.arraycopy(randomReferenceList, 0,referenceList, 0, referenceList.length); 
           
            victim= new String[referenceLength];
           hit = new boolean[referenceLength];
        for(int i=0; i<referenceLength; i++)
        {
            flag = true;
          
            for(int j=0; j<frame; j++)
            {
                 if(buffer[j].charAt(1) == referenceList[i].charAt(1))
                {
                  buffer[j]=referenceList[i];
                    flag = false;
                    pageHit++;
                    hit[i] = true;
                    break;
                }
            }
            if(num == frame)
                num = 0;
          
            if(flag)
            {
             String string = buffer[num];
            String substring = "W";
           if(string.contains(substring)){ 
              diskAccesses++;
           }
                
                
                victim[i]=buffer[num];
                buffer[num] = referenceList[i];
                pageFaults++;
                num++;
            }
            
      
        } 
        
      
      jTextArea2.append("FIFO-->Number of Page Hits : "+pageHit);
    
             
      
      //optimal
       int   pointer = 0;pageFaults = 0; int dirtyBit =-1;
         int numHITs;
        boolean isFull = false;
        boolean[] Hit;
        int[] Fault;
        String  MemoryTabel[][];
        diskAccesses=0;
        
        referenceList=new String[randomReferenceList.length] ;
        referenceLength = referenceList.length;
        System.arraycopy(randomReferenceList, 0,referenceList, 0, referenceList.length); 
       
        victim=new String[referenceLength];
        MemoryTabel = new String[referenceLength][frame];
        buffer = new String[frame];
        Hit = new boolean[referenceLength];
        Fault = new int[referenceLength];
        
        
        for(int j = 0; j < frame; j++)
        {
            buffer[j] = "  ";
        }

        
      
       
        for(int i = 0; i < referenceLength; i++)
        {
            int search = -1;
            for(int j = 0; j < frame; j++)
            {
                 if(buffer[j].charAt(1) == referenceList[i].charAt(1))
                {
                  buffer[j]=referenceList[i];
                    search = j;
                    Hit[i] = true;
                    Fault[i] = pageFaults;
                    break;
                }
            }

            if(search == -1)
            {
                if(isFull)
                {
                    int[] pointer2 = new int[frame];
                    boolean index_flag[] = new boolean[frame];
                    for(int j = i + 1; j < referenceLength; j++)
                    {
                        for(int k = 0; k < frame; k++)
                        {
                            if((referenceList[j].charAt(1) == buffer[k].charAt(1)) && (index_flag[k] == false))
                            {
                             
                            
                                pointer2[k] = j;
                                index_flag[k] = true;
                                break;
                            }
                        }
                    }
                    int flagMax = pointer2[0];
                    pointer = 0;
                    if(flagMax == 0)
                    {
                        flagMax = 200;
                    }

                    for(int j = 0; j < frame; j++)
                    {
                        if(pointer2[j] == 0)
                        {
                            pointer2[j] = 200;
                        }

                        if(pointer2[j] > flagMax)
                        {
                            flagMax = pointer2[j];
                            pointer = j;
                        }
                    }
                }
                
               

            String substring = "W";
           if(buffer[pointer].contains(substring)){
              diskAccesses++;
           }
           
               victim[i]= buffer[pointer];
               
                buffer[pointer] = referenceList[i];
                pageFaults++;
                Fault[i] = pageFaults;
                if(!isFull)
                {
                    pointer++;
                    if(pointer == frame)
                    {
                        pointer = 0;
                        isFull = true;
                    }
                }
            }

            for(int j = 0; j < frame; j++)
            {
                MemoryTabel[i][j] = buffer[j];
            }
        }

        for(int i = 0; i < referenceLength; i++)
        {   
            
         
            for(int j = 0; j < frame; j++)
            {
                if (MemoryTabel[i][j] == " ")
                {
                
                } else
                {
                 
                }
            }
          
        }
         numHITs= referenceList.length-pageFaults;
         jTextArea2.append("\nOptimal-->Number of Page Hits: " + numHITs);
        
                 
         //LRU
         pointer = 0;
        int  fault = 0;
         diskAccesses=0;
         referenceLength = 0;
        Boolean full = false;
        String mem[];
       
        ArrayList<String> stack = new ArrayList<String>();
        referenceList=new String[randomReferenceList.length];

      
       referenceList=new String[randomReferenceList.length] ;
       referenceLength = referenceList.length;
        System.arraycopy(randomReferenceList, 0,referenceList, 0, referenceList.length); 

        referenceLength=referenceList.length;
          victim= new String[referenceLength];

  
      
        mem = new String[frame];
        for(int j = 0; j < frame; j++)
            mem[j] = "  ";

      
        for(int i = 0; i < referenceLength; i++){
            if(stack.contains(referenceList[i])){
                stack.remove(stack.indexOf(referenceList[i]));
            }
            stack.add(referenceList[i]);
            int search = -1;
            for(int j = 0; j < frame; j++){
                if(mem[j].charAt(1)==(referenceList[i].charAt(1))){
                      mem[j]=referenceList[i];
                    search = j;

               
                    for(int w = 0; w < frame; w++){
                    
                    }
               
                    break;
                }
            }
            if(search == -1){
                if(full){
                    int min_loc = referenceLength;
                    for(int j = 0; j < frame; j++){     
                        if(stack.contains(mem[j])){ 
                            int temp = stack.indexOf(mem[j]);
                            if(temp < min_loc){

                                min_loc = temp;
                                pointer = j;
                            }
                        }
                    }
                }
                
               String string = mem[pointer];
            String substring = "W";
           if(string.contains(substring)){
              diskAccesses++;
           }
                
                victim[i]=mem[pointer];
                mem[pointer] = referenceList[i];
                fault++;
          
                for(int w = 0; w < frame; w++){
              
                }
                
            
                pointer++;
                if(pointer == frame){
                    pointer = 0;
                    full = true;
                }
            }
        }
       
        jTextArea2.append("\nLRU-->Number of Page Hits: " +(referenceLength-fault)+"\n" );
     
   
            
           
        }
        if(choice2Comparison.getSelectedItem().equals("Number Of Disk Accesses")){
          
             jTextArea2.setText(" ");
        int referenceLength,num=0, pageHit=0,diskAccesses=0,pageFaults=0;
        String buffer[], victim[],referenceList[];
        boolean flag = true;
        boolean hit[];
     
        buffer = new String[frame];
      
      
        for(int i=0; i<frame; i++)
        {
            buffer[i] = "  ";
        }
      
        referenceList=new String[randomReferenceList.length] ;
        referenceLength = referenceList.length;
        System.arraycopy(randomReferenceList, 0,referenceList, 0, referenceList.length); 
           
            victim= new String[referenceLength];
           hit = new boolean[referenceLength];
        for(int i=0; i<referenceLength; i++)
        {
            flag = true;
            
            for(int j=0; j<frame; j++)
            {
                if(buffer[j].charAt(1)==(referenceList[i].charAt(1)))
                {
                    buffer[j]=referenceList[i];
                    flag = false;
                    pageHit++;
                    hit[i] = true;
                    break;
                }
            }
            if(num == frame)
                num = 0;
          
            if(flag)
            {
             String string = buffer[num];
            String substring = "W";
           if(string.contains(substring)){ 
              diskAccesses++;
           }
                
                
                victim[i]=buffer[num];
                buffer[num] = referenceList[i];
                pageFaults++;
                num++;
            }
            
      
        } 
        
      jTextArea2.append("FIFO-->Number of Disk Accesses: "+diskAccesses+"\n");
    
              //optimal
       int   pointer = 0;pageFaults = 0; int dirtyBit =-1;
          int numHITs;
        boolean isFull = false;
        boolean[] Hit;
        int[] Fault;
        String  MemoryTabel[][];
        diskAccesses=0;
        
        referenceList=new String[randomReferenceList.length] ;
        referenceLength = referenceList.length;
        System.arraycopy(randomReferenceList, 0,referenceList, 0, referenceList.length); 
       
        victim=new String[referenceLength];
        MemoryTabel = new String[referenceLength][frame];
        buffer = new String[frame];
        Hit = new boolean[referenceLength];
        Fault = new int[referenceLength];
        
        
        for(int j = 0; j < frame; j++)
        {
            buffer[j] = "  ";
        }

        
      
       
        for(int i = 0; i < referenceLength; i++)
        {
            int search = -1;
            for(int j = 0; j < frame; j++)
            {
                if(buffer[j].charAt(1)==(referenceList[i].charAt(1)))
                {   buffer[j]=referenceList[i];
                    search = j;
                    Hit[i] = true;
                    Fault[i] = pageFaults;
                    break;
                }
            }

            if(search == -1)
            {
                if(isFull)
                {
                    int[] pointer2 = new int[frame];
                    boolean index_flag[] = new boolean[frame];
                    for(int j = i + 1; j < referenceLength; j++)
                    {
                        for(int k = 0; k < frame; k++)
                        {
                            if((referenceList[j].charAt(1) == buffer[k].charAt(1)) && (index_flag[k] == false))
                            {
                                pointer2[k] = j;
                                index_flag[k] = true;
                                break;
                            }
                        }
                    }
                    int flagMax = pointer2[0];
                    pointer = 0;
                    if(flagMax == 0)
                    {
                        flagMax = 200;
                    }

                    for(int j = 0; j < frame; j++)
                    {
                        if(pointer2[j] == 0)
                        {
                            pointer2[j] = 200;
                        }

                        if(pointer2[j] > flagMax)
                        {
                            flagMax = pointer2[j];
                            pointer = j;
                        }
                    }
                }
                
               

            String substring = "W";
           if(buffer[pointer].contains(substring)){
              diskAccesses++;
           }
           
               victim[i]= buffer[pointer];
               
                buffer[pointer] = referenceList[i];
                pageFaults++;
                Fault[i] = pageFaults;
                if(!isFull)
                {
                    pointer++;
                    if(pointer == frame)
                    {
                        pointer = 0;
                        isFull = true;
                    }
                }
            }

            for(int j = 0; j < frame; j++)
            {
                MemoryTabel[i][j] = buffer[j];
            }
        }

        for(int i = 0; i < referenceLength; i++)
        {   
            
         
            for(int j = 0; j < frame; j++)
            {
                if (MemoryTabel[i][j] == " ")
                {
                
                } else
                {
                 
                }
            }
          
        }
         numHITs= referenceList.length-pageFaults;
       
        jTextArea2.append("Optimal-->Number of disk accesses: " + diskAccesses);
                 
       
            
                    
         //LRU
         pointer = 0;
        int fault = 0;
         diskAccesses=0;
         referenceLength = 0;
        Boolean full = false;
        String mem[];
       
        ArrayList<String> stack = new ArrayList<String>();
        referenceList=new String[randomReferenceList.length];

      
       referenceList=new String[randomReferenceList.length] ;
       referenceLength = referenceList.length;
        System.arraycopy(randomReferenceList, 0,referenceList, 0, referenceList.length); 

        referenceLength=referenceList.length;
          victim= new String[referenceLength];

  
      
        mem = new String[frame];
        for(int j = 0; j < frame; j++)
            mem[j] = "  ";

      
        for(int i = 0; i < referenceLength; i++){
            if(stack.contains(referenceList[i])){
                stack.remove(stack.indexOf(referenceList[i]));
            }
            stack.add(referenceList[i]);
            int search = -1;
            for(int j = 0; j < frame; j++){
                if(mem[j].charAt(1)==(referenceList[i].charAt(1))){
                     mem[j]=referenceList[i];
                    search = j;

               
                    for(int w = 0; w < frame; w++){
                    
                    }
               
                    break;
                }
            }
            if(search == -1){
                if(full){
                    int min_loc = referenceLength;
                    for(int j = 0; j < frame; j++){     
                        if(stack.contains(mem[j])){ 
                            int temp = stack.indexOf(mem[j]);
                            if(temp < min_loc){

                                min_loc = temp;
                                pointer = j;
                            }
                        }
                    }
                }
                
               String string = mem[pointer];
            String substring = "W";
           if(string.contains(substring)){
              diskAccesses++;
           }
                
                victim[i]=mem[pointer];
                mem[pointer] = referenceList[i];
                fault++;
          
                for(int w = 0; w < frame; w++){
              
                }
                
            
                pointer++;
                if(pointer == frame){
                    pointer = 0;
                    full = true;
                }
            }
        }
    
       jTextArea2.append("\nLRU-->Number of Disk Acssess: " + diskAccesses+"\n" );
    
    
      
      
      
 
            
        }
      
          
          
    }//GEN-LAST:event_CompareActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VirtualMemorySimulation_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VirtualMemorySimulation_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VirtualMemorySimulation_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VirtualMemorySimulation_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VirtualMemorySimulation_GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Compare;
    private javax.swing.JButton Enter;
    private javax.swing.JButton Run;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private java.awt.Choice choice1Algorthim;
    private java.awt.Choice choice2Comparison;
    private javax.swing.JButton jButton3;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1FRAMES;
    private javax.swing.JTextField jTextField2Pages;
    // End of variables declaration//GEN-END:variables
}
