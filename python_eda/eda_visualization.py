"""
=============================================================================
APARTMENT MAINTENANCE AND RESIDENT COMPLAINT MANAGEMENT SYSTEM
Course Alignment: 25ITC31 - Foundations of Data Science
Scenario 18 (Register No.: 18, 48, 78, 108)
Python Data Retrieval, Preprocessing, EDA, and Visualization Pipeline
=============================================================================
"""

import os
import sqlite3
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
from datetime import datetime

# Configure styles
sns.set_theme(style="whitegrid", palette="muted")
plt.rcParams.update({
    "font.family": "sans-serif",
    "font.size": 11,
    "axes.titlesize": 13,
    "axes.titleweight": "bold",
    "axes.labelsize": 11,
    "axes.labelweight": "bold",
    "xtick.labelsize": 10,
    "ytick.labelsize": 10,
    "figure.titlesize": 14,
    "figure.titleweight": "bold"
})

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
PROJECT_ROOT = os.path.dirname(BASE_DIR)
DB_PATH = os.path.join(PROJECT_ROOT, "database", "apartment_system.db")
OUTPUT_DIR = os.path.join(BASE_DIR, "output_visualizations")
os.makedirs(OUTPUT_DIR, exist_ok=True)

def connect_db():
    if not os.path.exists(DB_PATH):
        raise FileNotFoundError(f"Database file not found at {DB_PATH}. Please run database/init_db.py first.")
    return sqlite3.connect(DB_PATH)

def load_and_preprocess_data():
    print("[1/4] Retrieving datasets from SQLite database...")
    conn = connect_db()
    
    # Retrieve raw tables into DataFrames
    df_apartments = pd.read_sql_query("SELECT * FROM apartments", conn)
    df_residents = pd.read_sql_query("SELECT * FROM residents", conn)
    df_payments = pd.read_sql_query("SELECT * FROM maintenance_payments", conn)
    df_complaints = pd.read_sql_query("SELECT * FROM complaints", conn)
    conn.close()

    print("[2/4] Preprocessing datasets and feature engineering...")
    
    # Preprocessing: Maintenance Payments
    df_payments["due_date"] = pd.to_datetime(df_payments["due_date"])
    df_payments["payment_date"] = pd.to_datetime(df_payments["payment_date"])
    df_payments["amount_due"] = pd.to_numeric(df_payments["amount_due"])
    df_payments["amount_paid"] = pd.to_numeric(df_payments["amount_paid"])
    df_payments["balance"] = df_payments["amount_due"] - df_payments["amount_paid"]

    # Preprocessing: Complaints
    df_complaints["complaint_date"] = pd.to_datetime(df_complaints["complaint_date"])
    df_complaints["resolution_date"] = pd.to_datetime(df_complaints["resolution_date"])
    
    # Feature Engineering: Resolution time (days) for resolved complaints
    # If not resolved, calculate turnaround days until reference current date
    ref_date = pd.to_datetime("2026-03-20")
    df_complaints["turnaround_days"] = (
        df_complaints["resolution_date"].fillna(ref_date) - df_complaints["complaint_date"]
    ).dt.days

    # Merge complaints with apartment details to know the Block
    df_complaints = df_complaints.merge(
        df_apartments[["apartment_id", "block_number", "apartment_type"]],
        on="apartment_id",
        how="left"
    )

    print(f"Loaded: {len(df_apartments)} apartments, {len(df_residents)} residents, "
          f"{len(df_payments)} payment bills, {len(df_complaints)} complaints.")
    
    return df_apartments, df_residents, df_payments, df_complaints

def generate_visualizations(df_apartments, df_residents, df_payments, df_complaints):
    print("[3/4] Generating exploratory data visualizations...")

    # -------------------------------------------------------------------------
    # Chart 1: Maintenance Payment Status Breakdown (Donut Chart)
    # -------------------------------------------------------------------------
    fig, ax = plt.subplots(figsize=(8, 6), subplot_kw=dict(aspect="equal"))
    status_counts = df_payments["payment_status"].value_counts()
    colors = ["#2ecc71", "#f39c12", "#e74c3c"]
    
    wedges, texts, autotexts = ax.pie(
        status_counts.values,
        labels=status_counts.index,
        autopct='%1.1f%%',
        startangle=140,
        colors=colors,
        wedgeprops=dict(width=0.4, edgecolor='white', linewidth=2),
        textprops=dict(color="black", weight="bold")
    )
    ax.set_title("Maintenance Fee Payment Status Distribution (Q1 2026)", pad=20)
    plt.tight_layout()
    p1 = os.path.join(OUTPUT_DIR, "01_payment_status_distribution.png")
    plt.savefig(p1, dpi=300)
    plt.close()
    print(f" -> Saved: {p1}")

    # -------------------------------------------------------------------------
    # Chart 2: Category-wise Resident Complaints Frequency (Horizontal Bar Chart)
    # -------------------------------------------------------------------------
    fig, ax = plt.subplots(figsize=(10, 6))
    cat_counts = df_complaints["category"].value_counts().sort_values(ascending=True)
    bars = ax.barh(cat_counts.index, cat_counts.values, color="#3498db", edgecolor="#2980b9", height=0.6)
    
    # Annotate bar values
    for bar in bars:
        ax.text(bar.get_width() + 0.08, bar.get_y() + bar.get_height()/2, 
                f"{int(bar.get_width())}", va='center', ha='left', fontweight='bold', color="#2c3e50")

    ax.set_title("Frequency of Resident Complaints by Facility Category", pad=15)
    ax.set_xlabel("Number of Registered Complaints")
    ax.set_ylabel("Facility Category")
    ax.set_xlim(0, max(cat_counts.values) + 1)
    plt.tight_layout()
    p2 = os.path.join(OUTPUT_DIR, "02_complaints_by_category.png")
    plt.savefig(p2, dpi=300)
    plt.close()
    print(f" -> Saved: {p2}")

    # -------------------------------------------------------------------------
    # Chart 3: Complaint Lifecycle Status by Category (Stacked Bar Chart)
    # -------------------------------------------------------------------------
    fig, ax = plt.subplots(figsize=(10, 6))
    crosstab_status = pd.crosstab(df_complaints["category"], df_complaints["status"])
    for col in ["Open", "In Progress", "Resolved"]:
        if col not in crosstab_status.columns:
            crosstab_status[col] = 0
    crosstab_status = crosstab_status[["Resolved", "In Progress", "Open"]]
    
    crosstab_status.plot(
        kind="bar",
        stacked=True,
        color=["#2ecc71", "#f39c12", "#e74c3c"],
        edgecolor="black",
        linewidth=0.8,
        ax=ax
    )
    ax.set_title("Complaint Resolution Status Breakdown by Category", pad=15)
    ax.set_xlabel("Complaint Category")
    ax.set_ylabel("Total Number of Complaints")
    ax.legend(title="Current Status", frameon=True)
    plt.xticks(rotation=30, ha="right")
    plt.tight_layout()
    p3 = os.path.join(OUTPUT_DIR, "03_complaint_status_stacked.png")
    plt.savefig(p3, dpi=300)
    plt.close()
    print(f" -> Saved: {p3}")

    # -------------------------------------------------------------------------
    # Chart 4: Monthly Maintenance Demand vs Collection (Grouped Bar Chart)
    # -------------------------------------------------------------------------
    fig, ax = plt.subplots(figsize=(10, 6))
    monthly_summary = df_payments.groupby("billing_month").agg({
        "amount_due": "sum",
        "amount_paid": "sum"
    }).reset_index()

    x = np.arange(len(monthly_summary))
    width = 0.35

    rects1 = ax.bar(x - width/2, monthly_summary["amount_due"], width, label="Total Billed (Due)", color="#95a5a6", edgecolor="#7f8c8d")
    rects2 = ax.bar(x + width/2, monthly_summary["amount_paid"], width, label="Total Collected (Paid)", color="#27ae60", edgecolor="#2ecc71")

    ax.set_title("Monthly Maintenance Collection Performance (Demand vs Collection)", pad=15)
    ax.set_xlabel("Billing Month")
    ax.set_ylabel("Amount (₹)")
    ax.set_xticks(x)
    ax.set_xticklabels(monthly_summary["billing_month"])
    ax.legend(frameon=True)
    ax.yaxis.set_major_formatter('₹{x:,.0f}')

    for rect in rects1 + rects2:
        height = rect.get_height()
        ax.annotate(f'₹{int(height):,}',
                    xy=(rect.get_x() + rect.get_width() / 2, height),
                    xytext=(0, 3),  # 3 points vertical offset
                    textcoords="offset points",
                    ha='center', va='bottom', fontsize=9, fontweight='bold')

    plt.tight_layout()
    p4 = os.path.join(OUTPUT_DIR, "04_monthly_collection_trend.png")
    plt.savefig(p4, dpi=300)
    plt.close()
    print(f" -> Saved: {p4}")

    # -------------------------------------------------------------------------
    # Chart 5: Complaint Turnaround Time Distribution (Box Plot)
    # -------------------------------------------------------------------------
    fig, ax = plt.subplots(figsize=(10, 6))
    resolved_df = df_complaints[df_complaints["status"] == "Resolved"].copy()
    resolved_df["resolution_days"] = (resolved_df["resolution_date"] - resolved_df["complaint_date"]).dt.days

    sns.boxplot(
        data=resolved_df,
        x="category",
        y="resolution_days",
        palette="Set2",
        ax=ax,
        boxprops=dict(alpha=0.8)
    )
    sns.stripplot(
        data=resolved_df,
        x="category",
        y="resolution_days",
        color="black",
        size=6,
        jitter=0.2,
        ax=ax
    )
    ax.set_title("Resolution Turnaround Time (Days) Across Facility Categories", pad=15)
    ax.set_xlabel("Facility Category")
    ax.set_ylabel("Resolution Duration (Days)")
    plt.xticks(rotation=25, ha="right")
    plt.tight_layout()
    p5 = os.path.join(OUTPUT_DIR, "05_resolution_time_boxplot.png")
    plt.savefig(p5, dpi=300)
    plt.close()
    print(f" -> Saved: {p5}")

    # -------------------------------------------------------------------------
    # Chart 6: Heatmap of Facility Complaints by Block and Category
    # -------------------------------------------------------------------------
    fig, ax = plt.subplots(figsize=(9, 6))
    block_cat_matrix = pd.crosstab(df_complaints["block_number"], df_complaints["category"])
    
    sns.heatmap(
        block_cat_matrix,
        annot=True,
        fmt="d",
        cmap="YlGnBu",
        cbar_kws={'label': 'Complaint Count'},
        linewidths=1,
        linecolor='white',
        ax=ax
    )
    ax.set_title("Facility Complaint Density Heatmap (Block vs Category)", pad=15)
    ax.set_xlabel("Facility Category")
    ax.set_ylabel("Apartment Block")
    plt.xticks(rotation=30, ha="right")
    plt.tight_layout()
    p6 = os.path.join(OUTPUT_DIR, "06_complaint_block_heatmap.png")
    plt.savefig(p6, dpi=300)
    plt.close()
    print(f" -> Saved: {p6}")

    print("[4/4] All 6 exploratory visualizations successfully exported!")

if __name__ == "__main__":
    df_apt, df_res, df_pay, df_comp = load_and_preprocess_data()
    generate_visualizations(df_apt, df_res, df_pay, df_comp)
