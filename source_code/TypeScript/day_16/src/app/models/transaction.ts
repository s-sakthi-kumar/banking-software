export interface Transaction {
  id: number;
  userId: number;
  type: 'credit' | 'debit';
  amount: number;
  description: string;
  date: string;
}
