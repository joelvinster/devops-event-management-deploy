# 🛡️ End-to-End DevSecOps & Cloud Infrastructure: Event Management System

## 🌟 Project Overview
This project is a professional-grade **Event Management System** built with **Java 17**. It demonstrates a complete "Code-to-Cloud" workflow, integrating Infrastructure as Code (IaC), Automated Configuration, Containerization, and Security Auditing.

---

## 🏗️ 1. Infrastructure as Code (IaC) & Automation
We don't manually set up servers. We use automation to ensure the environment is reproducible and stable:
* **Terraform:** Used to provision AWS resources (EC2 instances, Security Groups, and VPCs) automatically.
* **Ansible:** Used for configuration management to install Docker, Java 17, and set up the Linux environment on the EC2 instance.

## 🚀 2. DevOps & CI/CD Pipeline
* **Docker:** The application is containerized for portability. We use multi-stage builds to keep the image size small and secure.
* **Jenkins:** (Referencing `Jenkinsfile`) Our pipeline automates the building of the JAR file and the creation of the Docker image.
* **Monitoring:** Integrated with **Prometheus** and **Grafana** to monitor server metrics and application health in real-time.

## 🛡️ 3. DevSecOps (Security First)
Security is shifted left in our pipeline using **Trivy**:
* **Vulnerability Scanning:** Every build is scanned for known CVEs.
* **Scan Results:** ✅ **PASSED** (0 Critical, 0 High vulnerabilities found).
* **Audit Log:** See `trivy_report.txt` for the full security breakdown.

---

## 🔧 Deployment Steps

### Step 1: Provision & Configure
```bash
# Deploy AWS Infrastructure
cd infrastructure/terraform && terraform apply 

# Configure the Server
cd infrastructure/ansible && ansible-playbook setup.yml
