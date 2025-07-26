/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import java.util.InputMismatchException;
import java.util.Scanner;
import ADT.DynamicList;
import Entity.Patient;
import Entity.Consultation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDateTime;

/**
 *
 * @author leekeezhan
 */
public class ConsultationManagement {
    private DynamicList<Patient> queue = new DynamicList<>();
    private DynamicList<Consultation> consultationHistory = new DynamicList<>();

    private int consultationCounter = 1001;

    // 1️⃣ 病人加入队列
    public void enqueuePatient(Patient p) {
        p.setQueueNumber(queue.size() + 1); // 排号 = 队列长度 + 1
        p.setQueueStatus("Waiting");
        queue.add(p);
    }

    // 2️⃣ 查看当前排队人数
    public int getQueueSize() {
        return queue.size();
    }

    // 3️⃣ 获取当前等待的病人（peek）
    public Patient getNextPatient() {
        if (queue.isEmpty()) return null;
        return queue.getFirst();
    }

    // 4️⃣ 开始 consultation（并出队）
    public Consultation startConsultation(String doctorId, String symptoms) {
        if (queue.isEmpty()) return null;

        Patient patient = queue.remove(0);
        patient.setQueueStatus("Consulted");

        String consultationId = "C" + consultationCounter++;
        Consultation c = new Consultation(
            consultationId,
            patient.getPatientID(),
            doctorId,
            LocalDateTime.now(),
            symptoms,
            ""
        );
        consultationHistory.add(c);
        return c;
    }

    // 5️⃣ 结束 consultation（填诊断）
    public boolean endConsultation(String consultationId, String diagnosis) {
        for (int i = 0; i < consultationHistory.size(); i++) {
            Consultation c = consultationHistory.get(i);
            if (c.getConsultationId().equalsIgnoreCase(consultationId)) {
                c.setDiagnosis(diagnosis);
                return true;
            }
        }
        return false;
    }

    // 6️⃣ 打印全部历史记录
    public void printConsultationHistory() {
        for (int i = 0; i < consultationHistory.size(); i++) {
            System.out.println(consultationHistory.get(i));
        }
    }
}

