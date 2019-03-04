package mx.edu.ittepic.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.PacientesViewHolder> {
    ArrayList<PacientesVo> listPacientes;


    public Adapter(ArrayList<PacientesVo> listPacientes ) {
        this.listPacientes = listPacientes;
    }

    @Override
    public PacientesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, null,false);
        return new PacientesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PacientesViewHolder holder, int i) {
        holder.id.setText(listPacientes.get(i).getId());
        holder.nombre.setText(listPacientes.get(i).getNombre());
        holder.rfc.setText(listPacientes.get(i).getRfc());
        holder.celular.setText(listPacientes.get(i).getCelular());
        holder.email.setText(listPacientes.get(i).getEmail());
        holder.fecha.setText(listPacientes.get(i).getFecha());

    }

    @Override
    public int getItemCount() {
        return listPacientes.size();
    }

    public class PacientesViewHolder extends RecyclerView.ViewHolder {
        TextView id, nombre , rfc, celular, email, fecha;

        public PacientesViewHolder(View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.tid);
            nombre=itemView.findViewById(R.id.tnombre);
            rfc=itemView.findViewById(R.id.trfc);
            celular=itemView.findViewById(R.id.tcelular);
            email=itemView.findViewById(R.id.temail);
            fecha=itemView.findViewById(R.id.tfecha);

        }
    }
}
