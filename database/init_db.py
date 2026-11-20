"""
Database Initialization Script
Initializes the SQLite database with schema.sql and seed_data.sql.
"""
import os
import sqlite3

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
DB_PATH = os.path.join(BASE_DIR, "apartment_system.db")
SCHEMA_PATH = os.path.join(BASE_DIR, "schema.sql")
SEED_PATH = os.path.join(BASE_DIR, "seed_data.sql")

def initialize_database():
    print(f"Connecting to database at: {DB_PATH}")
    if os.path.exists(DB_PATH):
        os.remove(DB_PATH)
        print("Removed existing database file.")

    conn = sqlite3.connect(DB_PATH)
    cursor = conn.cursor()

    # Read and execute schema
    with open(SCHEMA_PATH, "r", encoding="utf-8") as f:
        schema_sql = f.read()
    cursor.executescript(schema_sql)
    print("Schema applied successfully.")

    # Read and execute seed data
    with open(SEED_PATH, "r", encoding="utf-8") as f:
        seed_sql = f.read()
    cursor.executescript(seed_sql)
    print("Seed data inserted successfully.")

    conn.commit()

    # Verify counts
    tables = ["apartments", "residents", "maintenance_payments", "complaints"]
    for table in tables:
        cursor.execute(f"SELECT COUNT(*) FROM {table}")
        count = cursor.fetchone()[0]
        print(f" - {table}: {count} records")

    conn.close()
    print("Database initialization complete.")

if __name__ == "__main__":
    initialize_database()
